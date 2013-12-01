package com.zhangxaochen.sensordataxml;


import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.example.mysensorlistener.MySensorListener;
//import com.zhangxaochen.huaweiproj.R.id;

@Root(name="session")
public class NewSessionNode extends XmlRootNode{
	@Element(name="begin-time")
	private double beginTime=-1;
	

	@Element(name="end-time")
	private double endTime=-1;
	
	@Element(name="thread-count")
	private int threadCnt=0;
	
	@Element(name="threads")
	private ThreadsNode threadsNode=new ThreadsNode();
	
	@Override
	public void addNode(MySensorListener.MySensorData sensorData) {
		LinkedList<float[]> abuf = sensorData.getAbuf();
		LinkedList<float[]> gbuf = sensorData.getGbuf();
		LinkedList<float[]> mbuf = sensorData.getMbuf();
		LinkedList<float[]> rbuf = sensorData.getRbuf();
//		LinkedList<Long> tsbuf=sensorData.getTbuf();
		
		LinkedList<Double> aTsBuf=sensorData.getATsBuf();
		LinkedList<Double> gTsBuf=sensorData.getGTsBuf();
		LinkedList<Double> mTsBuf=sensorData.getMTsBuf();
		LinkedList<Double> rTsBuf=sensorData.getRTsBuf();
		
		ArrayList<LinkedList<float[]>> dataBufs=new ArrayList<LinkedList<float[]>>();
		dataBufs.add(abuf);
		dataBufs.add(gbuf);
		dataBufs.add(mbuf);
		dataBufs.add(rbuf);
		
		ArrayList<LinkedList<Double>> tsBufs=new ArrayList<LinkedList<Double>>();
		tsBufs.add(aTsBuf);
		tsBufs.add(gTsBuf);
		tsBufs.add(mTsBuf);
		tsBufs.add(rTsBuf);
		

		// ��һ�� Node �ڵ㣺
//		NodeNode node = new NodeNode();
//		nodes.nodeList.add(node);
		
		//----------һ���ֻ�ֻ��һ���ڵ�
		ThreadNode threadNode=new ThreadNode();
		threadsNode.threadList.add(threadNode);
		threadCnt++;
		
		threadNode.setThreadName(0);
		//A,G,M,R
		threadNode.setChannelCnt(4);
//		threadNode.getChannelsNode().channelList.add(object)
		
		
		String names="agmr";
//		LinkedList<String> channelNames=new LinkedList<String>();
//		ArrayList<String> channelNames2=new ArrayList<String>();
		String[] channelNames={"a", "g", "m", "r", "la", "lawf"};
		
//		names.charAt(0);
		
//		ChannelNode channelNode=new ChannelNode();
//		channelNode.setChannelName("a");
		for(int i=0; i<4; i++){
			ChannelNode channelNode=new ChannelNode();
			threadNode.getChannelsNode().channelList.add(channelNode);
			
			channelNode.setChannelName(""+names.charAt(i));
			
			LinkedList<float[]> dataBuf=dataBufs.get(i);
			LinkedList<Double> tsBuf=tsBufs.get(i);
			assert dataBuf.size()==tsBuf.size();
			
			int frameCnt=dataBuf.size();
			channelNode.setFrameCnt(frameCnt);
			for(int idx=0; idx<frameCnt; idx++){
//				channelNode.getFramesNode().frameList
				FrameNode frameNode=new FrameNode();
				channelNode.getFramesNode().frameList.add(frameNode);
				
				frameNode.setIndex(idx);
				frameNode.setTimeStamp(tsBuf.poll());
				
				ValueNode valueNode=new ValueNode();
				float x=dataBuf.getFirst()[0];
				float y=dataBuf.getFirst()[1];
				float z=dataBuf.getFirst()[2];
				dataBuf.poll();
				valueNode.x=x;
				valueNode.y=y;
				valueNode.z=z;
				
				if(i==3){
					float w=(float) Math.sqrt(1-x*x-y*y-z*z);
					valueNode.w=w;					
				}
				frameNode.setValueNode(valueNode);
			}//for
		}//for
		
	}//addNode
	
	@Override
	public void clear(){
		threadsNode.threadList.clear();
		
		threadCnt=0;
		endTime=-1;
		beginTime=-1;
	}
	
	public double getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(double beginTime) {
		this.beginTime = beginTime;
	}

	public double getEndTime() {
		return endTime;
	}

	public void setEndTime(double endTime) {
		this.endTime = endTime;
	}


}//NewSession

//------���� NodesNode, btw, ��ʵһ���ֻ�ֻ��һ�� NodeNode
class ThreadsNode{
	@ElementList(entry="thread", inline=true)
	List<ThreadNode> threadList=new ArrayList<ThreadNode>();
	
}//ThreadsNode

//---���� DataNode
class ThreadNode{
	@Element(name="name")
	private long threadName=-1; 
	
	@Element(name="channel-count")
	private int channelCnt=0;
	
	@Element(name="channels")
	private ChannelsNode channelsNode=new ChannelsNode();

	public long getThreadName() {
		return threadName;
	}

	public void setThreadName(long threadName) {
		this.threadName = threadName;
	}

	public int getChannelCnt() {
		return channelCnt;
	}

	public void setChannelCnt(int channelCnt) {
		this.channelCnt = channelCnt;
	}

	public ChannelsNode getChannelsNode() {
		return channelsNode;
	}

	public void setChannelsNode(ChannelsNode channelsNode) {
		this.channelsNode = channelsNode;
	}
	
}//ThreadNode

//--------�¼� channels
class ChannelsNode{
	
	@ElementList(entry="channel", inline=true)
	List<ChannelNode> channelList=new ArrayList<ChannelNode>();
	
}//ChannelsNode

class ChannelNode{
	@Element(name="name")
	private String channelName="";
	
	@Element(name="frame-count")
	private int frameCnt=0;
	
	@Element(name="frames")
	private FramesNode framesNode=new FramesNode();

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public int getFrameCnt() {
		return frameCnt;
	}

	public void setFrameCnt(int frameCnt) {
		this.frameCnt = frameCnt;
	}

	public FramesNode getFramesNode() {
		return framesNode;
	}

	public void setFramesNode(FramesNode framesNode) {
		this.framesNode = framesNode;
	}
	
	
}//ChannelNode

class FramesNode{
	@ElementList(entry="frame", inline=true)
	List<FrameNode> frameList=new ArrayList<FrameNode>();
}//FramesNode

//-----�����洢���������ݵĵط�
class FrameNode{
	@Element(name="index")
	private int index=0;
	
	@Element(name="time")
	private double timeStamp=-1; 
	
	@Element(name="value")
	private ValueNode valueNode=new ValueNode();

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public void setTimeStamp(double timeStamp) {
		this.timeStamp = timeStamp;
	}

	public double getTimeStamp() {
		return timeStamp;
	}

	public ValueNode getValueNode() {
		return valueNode;
	}

	public void setValueNode(ValueNode valueNode) {
		this.valueNode = valueNode;
	}
	
	
}//FrameNode

class ValueNode{
	@Element(name="x")
	float x=-1;
	
	@Element(name="y")
	float y=-1;
	
	@Element(name="z")
	float z=-1;
	
	@Element(name="w", required=false)
	float w;
	
}//ValueNode