package com.zhangxaochen.sensordataxml;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.example.mysensorlistener.MySensorListener;


@Root(name = "CaptureSession")
public class CaptureSessionNode extends XmlRootNode{

	/**
	 * �ƺ�һ�� CaptureSession ֻ��һ�� Nodes �ڵ㣿
	 */
	@Element(name = "Nodes")
	private NodesNode nodes = new NodesNode();

	public NodesNode getNodes() {
		return nodes;
	}

	@Deprecated
	public void dataToObj() {
		// ...
	}

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
		

		// ��һ�� Node �ڵ㣺
		NodeNode node = new NodeNode();
		nodes.nodeList.add(node);

		int frames = abuf.size(); // //�������� buf.size һ��
		node.setFrames(frames);

		for (int i = 0; i < frames; i++) {
			DataNode dataNode = new DataNode();
			node.datas.add(dataNode);

			//����ʱ�����
//			dataNode.setTs(tsbuf.poll());
			
			float[] tuple;
			tuple = abuf.poll();
			if (tuple != null) {
				dataNode.setAx(tuple[0]);
				dataNode.setAy(tuple[1]);
				dataNode.setAz(tuple[2]);
			}else{
				System.out.println("abuf!!!!!"+i);
			}

			tuple = gbuf.poll();
			if (tuple != null) {
				dataNode.setGx(tuple[0]);
				dataNode.setGy(tuple[1]);
				dataNode.setGz(tuple[2]);
			}else{
				System.out.println("gbuf!!!!!"+i);
			}

			tuple = mbuf.poll();
			if (tuple != null) {
				dataNode.setMx(tuple[0]);
				dataNode.setMy(tuple[1]);
				dataNode.setMz(tuple[2]);
			}else{
				System.out.println("mbuf!!!!!"+i);
			}

			tuple = rbuf.poll();
			if (tuple != null) {
				float x=tuple[0],
						y=tuple[1],
						z=tuple[2];
				float w=(float) Math.sqrt(1-x*x-y*y-z*z);
				
				dataNode.setRw(w);
				dataNode.setRx(x);
				dataNode.setRy(y);
				dataNode.setRz(z);
//				if (tuple.length == 4)
//					dataNode.setRw(tuple[3]);
			}else{
				System.out.println("rbuf!!!!!"+i);
			}
		}// for
	}// addNode

	/**
	 * ��� CaptureSesson ����
	 */
	public void clearAllNodes() {
		nodes.nodeList.clear();
	}
}// CaptureSessionNode

class NodesNode {

	/**
	 * ��Ӧ Nodes �ڵ��µ� Node �ڵ��б� ����һ�εõ�һ�� Node
	 */
	@ElementList(entry = "Node", inline = true)
	List<NodeNode> nodeList = new ArrayList<NodeNode>();

}

class NodeNode {
	/**
	 * Ĭ��ֵ 1, һ���ֻ��������壬һ����� 1
	 */
	@Attribute
	private long phyId = 1;

	/**
	 * ÿ�β����õ�������֡��
	 */
	@Attribute
	private int frames = 0;

	/**
	 * ��Ӧ xml �ļ��е� Data �ڵ��б�
	 */
	@ElementList(entry = "Data", inline = true)
	List<DataNode> datas = new ArrayList<DataNode>();

	public long getPhyId() {
		return phyId;
	}

	public int getFrames() {
		return frames;
	}

	public void setFrames(int frames) {
		this.frames = frames;
	}

	public void setPhyId(long phyId) {
		this.phyId = phyId;
	}

}

/**
 * @author zhangxaochen
 * 
 * @hint G �� gyroscope, A �� acc�� R ��ʱ�� rotation vector ȡֵ����֪�� type_orientation
 *       ��ô���֣���M �� magnetic field
 * 
 * 
 */
class DataNode {
	private static int defaultValue=0;
	
	@Attribute
	private float Gx = defaultValue;
	@Attribute
	private float Gy = defaultValue;
	@Attribute
	private float Gz = defaultValue;

	@Attribute
	private float Mx = defaultValue;
	@Attribute
	private float My = defaultValue;
	@Attribute
	private float Mz = defaultValue;

	@Attribute
	private float Ax = defaultValue;
	@Attribute
	private float Ay = defaultValue;
	@Attribute
	private float Az = defaultValue;

	@Attribute
	private float Rw = defaultValue;
	@Attribute
	private float Rx = defaultValue;
	@Attribute
	private float Ry = defaultValue;
	@Attribute
	private float Rz = defaultValue;
	
	@Attribute
	private long timestamp=defaultValue;

	public float getGx() {
		return Gx;
	}

	public void setGx(float gx) {
		Gx = gx;
	}

	public float getGy() {
		return Gy;
	}

	public void setGy(float gy) {
		Gy = gy;
	}

	public float getGz() {
		return Gz;
	}

	public void setGz(float gz) {
		Gz = gz;
	}

	public float getMx() {
		return Mx;
	}

	public void setMx(float mx) {
		Mx = mx;
	}

	public float getMy() {
		return My;
	}

	public void setMy(float my) {
		My = my;
	}

	public float getMz() {
		return Mz;
	}

	public void setMz(float mz) {
		Mz = mz;
	}

	public float getAx() {
		return Ax;
	}

	public void setAx(float ax) {
		Ax = ax;
	}

	public float getAy() {
		return Ay;
	}

	public void setAy(float ay) {
		Ay = ay;
	}

	public float getAz() {
		return Az;
	}

	public void setAz(float az) {
		Az = az;
	}

	public float getRw() {
		return Rw;
	}

	public void setRw(float rw) {
		Rw = rw;
	}

	public float getRx() {
		return Rx;
	}

	public void setRx(float rx) {
		Rx = rx;
	}

	public float getRy() {
		return Ry;
	}

	public void setRy(float ry) {
		Ry = ry;
	}

	public float getRz() {
		return Rz;
	}

	public void setRz(float rz) {
		Rz = rz;
	}

	public long getTs() {
		return timestamp;
	}

	public void setTs(long ts) {
		this.timestamp = ts;
	}

	
}//DataNode
