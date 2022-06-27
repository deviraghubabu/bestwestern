package com.bw.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;
import java.util.Timer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class GenXSimulator {
	private Properties _instanceProps;
	private int _targetTrackIdSn = 0;
	private InetAddress _instanceIP = null;
	private List<GenxEvent> _genxEventList = null;
	public int _currentPlaybackNdx = 0;
	public int _delay = 5;
	public Timer _timer = null;
	private int _seqId = 0;

	public GenXSimulator() throws IOException
	{
		_instanceProps = new Properties();
		_instanceProps.loadFromXML(new FileInputStream("InstanceProperties.xml"));
		_seqId = Integer.parseInt(_instanceProps.getProperty("SeqId", "10000"));
//		_instanceProps.list(System.out);
	}

	public void setTargetTrackId(String trackId) {
		if (!trackId.startsWith("GENX_"))
			throw new IllegalArgumentException("Invalid GenX TrackID, must start with 'GENX_' followed by 8 digits");

		String snStr = trackId.substring(5);
		if (snStr.length() != 8)
			throw new IllegalArgumentException("Invalid GenX TrackID, must start with 'GENX_' followed by 8 digits");

		_targetTrackIdSn = Integer.parseInt(snStr);
	}

	public void setTripFilePath(String filePath) throws FileNotFoundException, IOException, ParseException {
		JSONParser jsonParser = new JSONParser();
		Object object = jsonParser.parse(new FileReader(filePath));

		JSONArray jsonArray = (JSONArray) object;
		_genxEventList = getListFromJsonArray(jsonArray);

		for (GenxEvent g : _genxEventList) {
			System.out.println(g.ToString());
		}
	}
	public void set360ExternalIP(String instanceIP) throws IOException {
//		String ipAddress = _instanceProps.getProperty(instanceName);
		if (instanceIP.length() == 0)
			throw new IllegalArgumentException("Invalid Instance Name, must be set up in the InstanceProperties.xml file");
		_instanceIP = InetAddress.getByName(instanceIP);
	}

	public void startTrip() throws IOException, InterruptedException {
		if (_targetTrackIdSn == 0)
			throw new IllegalArgumentException("Trarget TrackId is not set, use setTargetTrackId()");

		if ((_genxEventList == null) || (_genxEventList.size() == 0))
			throw new IllegalArgumentException("Trip event file not loaded, use setTripFilePath()");

		if (_instanceIP == null)
			throw new IllegalArgumentException("An instance of 360 has not been set, use set360InstanceName()");

		String rootPath = new File(".").getCanonicalPath();
		String instancesFilePath = rootPath + "\\InstanceProperties.xml";
		Properties instanceProps = new Properties();
		instanceProps.loadFromXML(new FileInputStream(instancesFilePath));
		
		tripTimer();
	}

	public void tripTimer() throws InterruptedException {
//		TimerTask repeatedTask = new TimerTask() {
//			public void run() {
		while (_currentPlaybackNdx < _genxEventList.size()) {
			GenxEvent genxEvent = _genxEventList.get(_currentPlaybackNdx);
			System.out.println("(TID: GENX_" + _targetTrackIdSn + ") TimerTask -> Playback Ndx: " + _currentPlaybackNdx + "   SeqId: " + _seqId + "   Delay: " + genxEvent.SecsToNextEvent);
			sendEvent(genxEvent);
			_currentPlaybackNdx++;
			_delay = (int) genxEvent.SecsToNextEvent * 1000; // change the period time
			Thread.sleep(_delay);
//			_timer.cancel(); // cancel time
//			try {
//				tripTimer(); // start the time again with a new period time
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
	}
//		_timer = new Timer();
//		_timer.schedule(task, _delay);



//		ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
//		long delay  = 1000L;
//		long period = 1000L;
//		executor.scheduleAtFixedRate(repeatedTask, delay, period, TimeUnit.MILLISECONDS);
//		Thread.sleep(2000);
//		executor.shutdown();
//	}

	public List<GenxEvent> getListFromJsonArray(JSONArray jArray) {
		List<GenxEvent> returnList = new ArrayList<GenxEvent>();
		for (int i = 0; i < jArray.size(); i++) {
			returnList.add(convertJsonItem((JSONObject) jArray.get(i)));
		}
		return returnList;
	}

	private GenxEvent convertJsonItem(JSONObject object) {
		Base64.Decoder decoder = Base64.getDecoder();
		String eventBytesStr = (String) object.get("EventBytes");
		byte[] eventBytes = decoder.decode(eventBytesStr);

		Long reasonCode = (Long) object.get("ReasonCode");
		String reasonString = (String) object.get("ReasonString");
		String eventTime = (String) object.get("EventTime");
		Long secsToNextEvent = (Long) object.get("SecsToNextEvent");
		Long eventTimePosition = (Long) object.get("EventTimePosition");

		return new GenxEvent(eventBytes, reasonCode, reasonString, eventTime, secsToNextEvent, eventTimePosition);
	}

	public void sendUDP(byte[] bytes) throws IOException {
		DatagramSocket socket = new DatagramSocket();
		DatagramPacket packet = new DatagramPacket(bytes, bytes.length, _instanceIP, Constants.genXport);
		socket.send(packet);
		socket.close();

		FileOutputStream out = new FileOutputStream("InstanceProperties.xml");
		_instanceProps.setProperty("SeqId", Integer.toString(_seqId));
		_instanceProps.storeToXML(out, "--- 360 Instance Properties for GenXSimulator ---");
		out.close();
	}

	public void sendEvent(GenxEvent genxEvent) {
		byte[] eventBytes = genxEvent.EventBytes;

		ByteBuffer byteBuffer = ByteBuffer.allocate(4);
		byteBuffer.putInt(_targetTrackIdSn);
		byte[] snBytes = byteBuffer.array();

		eventBytes[1] = snBytes[0];
		eventBytes[2] = snBytes[1];
		eventBytes[3] = snBytes[2];
		eventBytes[4] = snBytes[3];

		byteBuffer = ByteBuffer.allocate(4);
		byteBuffer.putInt(_seqId++);
		byte[] seqIdBytes = byteBuffer.array();

		eventBytes[6] = seqIdBytes[0];
		eventBytes[7] = seqIdBytes[1];
		eventBytes[8] = seqIdBytes[2];
		eventBytes[9] = seqIdBytes[3];

		int eSecs = (int) Instant.now().getEpochSecond();
		byteBuffer = ByteBuffer.allocate(4);
		byteBuffer.putInt(eSecs);
		byte[] epochBytes = byteBuffer.array();

		int n = (int) genxEvent.EventTimePosition;
		eventBytes[n] = epochBytes[0];
		eventBytes[n + 1] = epochBytes[1];
		eventBytes[n + 2] = epochBytes[2];
		eventBytes[n + 3] = epochBytes[3];

		try {
			sendUDP(eventBytes);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public class GenxEvent {
		public byte[] EventBytes;
		public long ReasonCode;
		public String ReasonString;
		public String EventTime;
		public long SecsToNextEvent;
		public long EventTimePosition;

		public GenxEvent(byte[] eventBytes, long reasonCode, String reasonString, String eventTime,
				long secsToNextEvent, long eventTimePosition) {
			EventBytes = eventBytes;
			ReasonCode = reasonCode;
			ReasonString = reasonString;
			EventTime = eventTime;
			SecsToNextEvent = secsToNextEvent;
			EventTimePosition = eventTimePosition;
		}

		public String ToString() {
			StringBuilder sb = new StringBuilder();
			sb.append("Secs to Next: " + SecsToNextEvent + " Bytes: ");
			for (byte b : EventBytes) {
				sb.append(String.format("%02x ", b));
			}
			return sb.toString();
		}
	}
}