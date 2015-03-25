package com.donkersg.pingwatcher.graph;

import java.awt.Point;
import java.util.ArrayList;

public class GraphData {

	private ArrayList<Point> data;

	public String xAxisName;
	public String yAxisName;

	private int xMax;
	private int yMax;
	private int xMin;
	private int yMin;

	public GraphData() {
		data = new ArrayList<Point>();
	}

	public int points() {
		return data.size();
	}

	public int getRangeX() {
		return xMax - xMin;
	}

	public int getRangeY() {
		return yMax - yMin;
	}

	public int getMaxX() {
		return xMax;
	}

	public int getMinX() {
		return xMin;
	}

	public int getMaxY() {
		return yMax;
	}

	public int getMinY() {
		return yMin;
	}

	private void CalculateRange() {

		if (data.size() == 0) {
			return;
		}

		this.xMax = this.xMin = data.get(0).x;
		this.yMax = this.yMin = data.get(0).y;

		for (Point point : data) {
			if (point.x > this.xMax) {
				this.xMax = point.x;
			} else if (point.x < this.xMin) {
				this.xMin = point.x;
			}

			if (point.y > this.yMax) {
				this.yMax = point.y;
			} else if (point.y < this.yMin) {

				this.yMin = point.y;
			}
		}
	}

	public void addPoint(Point point) {
		data.add(point);
		this.CalculateRange();
	}

	public void setPoints(ArrayList<Point> data) {
		this.data = data;
		this.CalculateRange();
	}

	public ArrayList<Point> getPoints() {
		return this.data;
	}
}