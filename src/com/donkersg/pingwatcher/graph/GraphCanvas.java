package com.donkersg.pingwatcher.graph;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Line2D;

class GraphCanvas extends Canvas {

	public final int MARGIN_WEST = 50;
	public final int MARGIN_NORTH = 10;
	public final int MARGIN_SOUTH = 10;
	public final int MARGIN_EAST = 20;

	private static final long serialVersionUID = 1L;

	private int lowMs;
	private int highMs;
	private Color bgColor;
	private Image bufferImage;
	private Graphics2D offScreen;

	private GraphData graphdata;

	public void setLowMsMark(int lowMs) {
		this.lowMs = lowMs;
	}

	public void setHighMsMark(int highMs) {
		this.highMs = highMs;
	}

	public void updateGraph(GraphData graphdata) {
		this.graphdata = graphdata;
		repaint();
	}

	public GraphCanvas() {
		bgColor = new Color(0x222222);
		lowMs = 50;
		highMs = 100;
	}

	public void paint(Graphics g) {
		bufferImage = createImage(this.getSize().width, this.getSize().height);
		offScreen = (Graphics2D) bufferImage.getGraphics();

		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		offScreen.setRenderingHints(rh);

		drawBackgrond(offScreen);
		drawAxis(offScreen);
		drawPoints(offScreen);
		drawSectionLines(offScreen);

		g.drawImage(bufferImage, 0, 0, null);
	}

	public void drawAxis(Graphics2D g) {
		int height = this.getSize().height;
		int width = this.getSize().width;

		g.setColor(Color.WHITE);

		g.drawLine(MARGIN_WEST, MARGIN_NORTH, MARGIN_WEST, height - MARGIN_SOUTH);
		g.drawLine(MARGIN_WEST, height - MARGIN_SOUTH, width - MARGIN_EAST, height - MARGIN_SOUTH);

		if (graphdata == null)
			return;

		g.drawString("0ms", 5, height - MARGIN_NORTH);
		g.drawString("" + getGraphHeight() + "ms", 5, MARGIN_NORTH + 5);
	}

	public int getGraphHeight() {
		return highMs + 50;
	}

	public void drawSectionLines(Graphics2D g) {

		int width = this.getSize().width;

		int lowLineHeight = getPointHeight(lowMs);
		int highLineHeight = getPointHeight(highMs);

		g.setColor(Color.GREEN);
		g.drawLine(MARGIN_WEST, lowLineHeight, width - MARGIN_EAST, lowLineHeight);

		g.setColor(Color.RED);
		g.drawLine(MARGIN_WEST, highLineHeight, width - MARGIN_EAST, highLineHeight);

		g.setColor(Color.WHITE);
		g.drawString(lowMs + "ms", 5, lowLineHeight + 5);
		g.drawString(highMs + "ms", 5, highLineHeight + 5);
	}

	public int getPointHeight(int ms) {
		int height = this.getSize().height;
		double msHeight = getMsHeight();

		return (height - (int) Math.round(msHeight * ms) - MARGIN_NORTH);
	}

	public double getMsHeight() {
		int height = this.getSize().height;

		if (graphdata == null)
			return (((double) height - MARGIN_SOUTH - MARGIN_NORTH) / 300);

		return (((double) height - MARGIN_SOUTH - MARGIN_NORTH) / getGraphHeight());
	}

	public void drawBackgrond(Graphics2D g) {
		g.setColor(bgColor);
		g.fillRect(0, 0, this.getSize().width, this.getSize().height);
	}

	public void drawPoints(Graphics2D g) {

		if (graphdata == null)
			return;

		g.setColor(Color.WHITE);

		int width = this.getSize().width;

		if (graphdata.points() > 0) {

			int pixelsEach = (int) Math.ceil((width - MARGIN_EAST - MARGIN_WEST) / graphdata.points());
			int xLocation = MARGIN_WEST;

			int yLocation;

			Point lastDrawPoint = null;

			for (Point point : graphdata.getPoints()) {

				xLocation += pixelsEach;
				yLocation = getPointHeight(point.y);

				if (lastDrawPoint != null) {
					g.draw(new Line2D.Float(lastDrawPoint.x, lastDrawPoint.y, xLocation, yLocation));
				}

				lastDrawPoint = new Point(xLocation, yLocation);
			}
		}
	}

	public Dimension getPreferredSize() {
		return new Dimension(450, 350);
	}
}
