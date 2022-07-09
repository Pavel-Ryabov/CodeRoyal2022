package xyz.pary.raic.coderoyal2022.debugging;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import xyz.pary.raic.coderoyal2022.model.Vec2;
import xyz.pary.raic.coderoyal2022.util.StreamUtil;

public abstract class DebugData {

    public abstract void writeTo(OutputStream stream) throws IOException;

    public static DebugData readFrom(InputStream stream) throws IOException {
        switch (StreamUtil.readInt(stream)) {
            case PlacedText.TAG:
                return PlacedText.readFrom(stream);
            case Circle.TAG:
                return Circle.readFrom(stream);
            case GradientCircle.TAG:
                return GradientCircle.readFrom(stream);
            case Ring.TAG:
                return Ring.readFrom(stream);
            case Pie.TAG:
                return Pie.readFrom(stream);
            case Arc.TAG:
                return Arc.readFrom(stream);
            case Rect.TAG:
                return Rect.readFrom(stream);
            case Polygon.TAG:
                return Polygon.readFrom(stream);
            case GradientPolygon.TAG:
                return GradientPolygon.readFrom(stream);
            case Segment.TAG:
                return Segment.readFrom(stream);
            case GradientSegment.TAG:
                return GradientSegment.readFrom(stream);
            case PolyLine.TAG:
                return PolyLine.readFrom(stream);
            case GradientPolyLine.TAG:
                return GradientPolyLine.readFrom(stream);
            default:
                throw new IOException("Unexpected tag value");
        }
    }

    public static class PlacedText extends DebugData {

        public static final int TAG = 0;

        private Vec2 position;

        public Vec2 getPosition() {
            return position;
        }

        public void setPosition(Vec2 value) {
            this.position = value;
        }

        private String text;

        public String getText() {
            return text;
        }

        public void setText(String value) {
            this.text = value;
        }

        private Vec2 alignment;

        public Vec2 getAlignment() {
            return alignment;
        }

        public void setAlignment(Vec2 value) {
            this.alignment = value;
        }

        private double size;

        public double getSize() {
            return size;
        }

        public void setSize(double value) {
            this.size = value;
        }

        private Color color;

        public Color getColor() {
            return color;
        }

        public void setColor(Color value) {
            this.color = value;
        }

        public PlacedText(Vec2 position, String text, Vec2 alignment, double size, Color color) {
            this.position = position;
            this.text = text;
            this.alignment = alignment;
            this.size = size;
            this.color = color;
        }

        public static PlacedText readFrom(InputStream stream) throws IOException {
            Vec2 position;
            position = Vec2.readFrom(stream);
            String text;
            text = StreamUtil.readString(stream);
            Vec2 alignment;
            alignment = Vec2.readFrom(stream);
            double size;
            size = StreamUtil.readDouble(stream);
            Color color;
            color = Color.readFrom(stream);
            return new PlacedText(position, text, alignment, size, color);
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
            position.writeTo(stream);
            StreamUtil.writeString(stream, text);
            alignment.writeTo(stream);
            StreamUtil.writeDouble(stream, size);
            color.writeTo(stream);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("PlacedText { ");
            stringBuilder.append("position: ");
            stringBuilder.append(String.valueOf(position));
            stringBuilder.append(", ");
            stringBuilder.append("text: ");
            stringBuilder.append('"' + text + '"');
            stringBuilder.append(", ");
            stringBuilder.append("alignment: ");
            stringBuilder.append(String.valueOf(alignment));
            stringBuilder.append(", ");
            stringBuilder.append("size: ");
            stringBuilder.append(String.valueOf(size));
            stringBuilder.append(", ");
            stringBuilder.append("color: ");
            stringBuilder.append(String.valueOf(color));
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class Circle extends DebugData {

        public static final int TAG = 1;

        private Vec2 position;

        public Vec2 getPosition() {
            return position;
        }

        public void setPosition(Vec2 value) {
            this.position = value;
        }

        private double radius;

        public double getRadius() {
            return radius;
        }

        public void setRadius(double value) {
            this.radius = value;
        }

        private Color color;

        public Color getColor() {
            return color;
        }

        public void setColor(Color value) {
            this.color = value;
        }

        public Circle(Vec2 position, double radius, Color color) {
            this.position = position;
            this.radius = radius;
            this.color = color;
        }

        public static Circle readFrom(InputStream stream) throws IOException {
            Vec2 position;
            position = Vec2.readFrom(stream);
            double radius;
            radius = StreamUtil.readDouble(stream);
            Color color;
            color = Color.readFrom(stream);
            return new Circle(position, radius, color);
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
            position.writeTo(stream);
            StreamUtil.writeDouble(stream, radius);
            color.writeTo(stream);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("Circle { ");
            stringBuilder.append("position: ");
            stringBuilder.append(String.valueOf(position));
            stringBuilder.append(", ");
            stringBuilder.append("radius: ");
            stringBuilder.append(String.valueOf(radius));
            stringBuilder.append(", ");
            stringBuilder.append("color: ");
            stringBuilder.append(String.valueOf(color));
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class GradientCircle extends DebugData {

        public static final int TAG = 2;

        private Vec2 position;

        public Vec2 getPosition() {
            return position;
        }

        public void setPosition(Vec2 value) {
            this.position = value;
        }

        private double radius;

        public double getRadius() {
            return radius;
        }

        public void setRadius(double value) {
            this.radius = value;
        }

        private Color innerColor;

        public Color getInnerColor() {
            return innerColor;
        }

        public void setInnerColor(Color value) {
            this.innerColor = value;
        }

        private Color outerColor;

        public Color getOuterColor() {
            return outerColor;
        }

        public void setOuterColor(Color value) {
            this.outerColor = value;
        }

        public GradientCircle(Vec2 position, double radius, Color innerColor, Color outerColor) {
            this.position = position;
            this.radius = radius;
            this.innerColor = innerColor;
            this.outerColor = outerColor;
        }

        public static GradientCircle readFrom(InputStream stream) throws IOException {
            Vec2 position;
            position = Vec2.readFrom(stream);
            double radius;
            radius = StreamUtil.readDouble(stream);
            Color innerColor;
            innerColor = Color.readFrom(stream);
            Color outerColor;
            outerColor = Color.readFrom(stream);
            return new GradientCircle(position, radius, innerColor, outerColor);
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
            position.writeTo(stream);
            StreamUtil.writeDouble(stream, radius);
            innerColor.writeTo(stream);
            outerColor.writeTo(stream);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("GradientCircle { ");
            stringBuilder.append("position: ");
            stringBuilder.append(String.valueOf(position));
            stringBuilder.append(", ");
            stringBuilder.append("radius: ");
            stringBuilder.append(String.valueOf(radius));
            stringBuilder.append(", ");
            stringBuilder.append("innerColor: ");
            stringBuilder.append(String.valueOf(innerColor));
            stringBuilder.append(", ");
            stringBuilder.append("outerColor: ");
            stringBuilder.append(String.valueOf(outerColor));
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class Ring extends DebugData {

        public static final int TAG = 3;

        private Vec2 position;

        public Vec2 getPosition() {
            return position;
        }

        public void setPosition(Vec2 value) {
            this.position = value;
        }

        private double radius;

        public double getRadius() {
            return radius;
        }

        public void setRadius(double value) {
            this.radius = value;
        }

        private double width;

        public double getWidth() {
            return width;
        }

        public void setWidth(double value) {
            this.width = value;
        }

        private Color color;

        public Color getColor() {
            return color;
        }

        public void setColor(Color value) {
            this.color = value;
        }

        public Ring(Vec2 position, double radius, double width, Color color) {
            this.position = position;
            this.radius = radius;
            this.width = width;
            this.color = color;
        }

        public static Ring readFrom(InputStream stream) throws IOException {
            Vec2 position;
            position = Vec2.readFrom(stream);
            double radius;
            radius = StreamUtil.readDouble(stream);
            double width;
            width = StreamUtil.readDouble(stream);
            Color color;
            color = Color.readFrom(stream);
            return new Ring(position, radius, width, color);
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
            position.writeTo(stream);
            StreamUtil.writeDouble(stream, radius);
            StreamUtil.writeDouble(stream, width);
            color.writeTo(stream);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("Ring { ");
            stringBuilder.append("position: ");
            stringBuilder.append(String.valueOf(position));
            stringBuilder.append(", ");
            stringBuilder.append("radius: ");
            stringBuilder.append(String.valueOf(radius));
            stringBuilder.append(", ");
            stringBuilder.append("width: ");
            stringBuilder.append(String.valueOf(width));
            stringBuilder.append(", ");
            stringBuilder.append("color: ");
            stringBuilder.append(String.valueOf(color));
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class Pie extends DebugData {

        public static final int TAG = 4;

        private Vec2 position;

        public Vec2 getPosition() {
            return position;
        }

        public void setPosition(Vec2 value) {
            this.position = value;
        }

        private double radius;

        public double getRadius() {
            return radius;
        }

        public void setRadius(double value) {
            this.radius = value;
        }

        private double startAngle;

        public double getStartAngle() {
            return startAngle;
        }

        public void setStartAngle(double value) {
            this.startAngle = value;
        }

        private double endAngle;

        public double getEndAngle() {
            return endAngle;
        }

        public void setEndAngle(double value) {
            this.endAngle = value;
        }

        private Color color;

        public Color getColor() {
            return color;
        }

        public void setColor(Color value) {
            this.color = value;
        }

        public Pie(Vec2 position, double radius, double startAngle, double endAngle, Color color) {
            this.position = position;
            this.radius = radius;
            this.startAngle = startAngle;
            this.endAngle = endAngle;
            this.color = color;
        }

        public static Pie readFrom(InputStream stream) throws IOException {
            Vec2 position;
            position = Vec2.readFrom(stream);
            double radius;
            radius = StreamUtil.readDouble(stream);
            double startAngle;
            startAngle = StreamUtil.readDouble(stream);
            double endAngle;
            endAngle = StreamUtil.readDouble(stream);
            Color color;
            color = Color.readFrom(stream);
            return new Pie(position, radius, startAngle, endAngle, color);
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
            position.writeTo(stream);
            StreamUtil.writeDouble(stream, radius);
            StreamUtil.writeDouble(stream, startAngle);
            StreamUtil.writeDouble(stream, endAngle);
            color.writeTo(stream);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("Pie { ");
            stringBuilder.append("position: ");
            stringBuilder.append(String.valueOf(position));
            stringBuilder.append(", ");
            stringBuilder.append("radius: ");
            stringBuilder.append(String.valueOf(radius));
            stringBuilder.append(", ");
            stringBuilder.append("startAngle: ");
            stringBuilder.append(String.valueOf(startAngle));
            stringBuilder.append(", ");
            stringBuilder.append("endAngle: ");
            stringBuilder.append(String.valueOf(endAngle));
            stringBuilder.append(", ");
            stringBuilder.append("color: ");
            stringBuilder.append(String.valueOf(color));
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class Arc extends DebugData {

        public static final int TAG = 5;

        private Vec2 position;

        public Vec2 getPosition() {
            return position;
        }

        public void setPosition(Vec2 value) {
            this.position = value;
        }

        private double radius;

        public double getRadius() {
            return radius;
        }

        public void setRadius(double value) {
            this.radius = value;
        }

        private double width;

        public double getWidth() {
            return width;
        }

        public void setWidth(double value) {
            this.width = value;
        }

        private double startAngle;

        public double getStartAngle() {
            return startAngle;
        }

        public void setStartAngle(double value) {
            this.startAngle = value;
        }

        private double endAngle;

        public double getEndAngle() {
            return endAngle;
        }

        public void setEndAngle(double value) {
            this.endAngle = value;
        }

        private Color color;

        public Color getColor() {
            return color;
        }

        public void setColor(Color value) {
            this.color = value;
        }

        public Arc(Vec2 position, double radius, double width, double startAngle, double endAngle, Color color) {
            this.position = position;
            this.radius = radius;
            this.width = width;
            this.startAngle = startAngle;
            this.endAngle = endAngle;
            this.color = color;
        }

        public static Arc readFrom(InputStream stream) throws IOException {
            Vec2 position;
            position = Vec2.readFrom(stream);
            double radius;
            radius = StreamUtil.readDouble(stream);
            double width;
            width = StreamUtil.readDouble(stream);
            double startAngle;
            startAngle = StreamUtil.readDouble(stream);
            double endAngle;
            endAngle = StreamUtil.readDouble(stream);
            Color color;
            color = Color.readFrom(stream);
            return new Arc(position, radius, width, startAngle, endAngle, color);
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
            position.writeTo(stream);
            StreamUtil.writeDouble(stream, radius);
            StreamUtil.writeDouble(stream, width);
            StreamUtil.writeDouble(stream, startAngle);
            StreamUtil.writeDouble(stream, endAngle);
            color.writeTo(stream);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("Arc { ");
            stringBuilder.append("position: ");
            stringBuilder.append(String.valueOf(position));
            stringBuilder.append(", ");
            stringBuilder.append("radius: ");
            stringBuilder.append(String.valueOf(radius));
            stringBuilder.append(", ");
            stringBuilder.append("width: ");
            stringBuilder.append(String.valueOf(width));
            stringBuilder.append(", ");
            stringBuilder.append("startAngle: ");
            stringBuilder.append(String.valueOf(startAngle));
            stringBuilder.append(", ");
            stringBuilder.append("endAngle: ");
            stringBuilder.append(String.valueOf(endAngle));
            stringBuilder.append(", ");
            stringBuilder.append("color: ");
            stringBuilder.append(String.valueOf(color));
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class Rect extends DebugData {

        public static final int TAG = 6;

        private Vec2 bottomLeft;

        public Vec2 getBottomLeft() {
            return bottomLeft;
        }

        public void setBottomLeft(Vec2 value) {
            this.bottomLeft = value;
        }

        private Vec2 size;

        public Vec2 getSize() {
            return size;
        }

        public void setSize(Vec2 value) {
            this.size = value;
        }

        private Color color;

        public Color getColor() {
            return color;
        }

        public void setColor(Color value) {
            this.color = value;
        }

        public Rect(Vec2 bottomLeft, Vec2 size, Color color) {
            this.bottomLeft = bottomLeft;
            this.size = size;
            this.color = color;
        }

        public static Rect readFrom(InputStream stream) throws IOException {
            Vec2 bottomLeft;
            bottomLeft = Vec2.readFrom(stream);
            Vec2 size;
            size = Vec2.readFrom(stream);
            Color color;
            color = Color.readFrom(stream);
            return new Rect(bottomLeft, size, color);
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
            bottomLeft.writeTo(stream);
            size.writeTo(stream);
            color.writeTo(stream);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("Rect { ");
            stringBuilder.append("bottomLeft: ");
            stringBuilder.append(String.valueOf(bottomLeft));
            stringBuilder.append(", ");
            stringBuilder.append("size: ");
            stringBuilder.append(String.valueOf(size));
            stringBuilder.append(", ");
            stringBuilder.append("color: ");
            stringBuilder.append(String.valueOf(color));
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class Polygon extends DebugData {

        public static final int TAG = 7;

        private Vec2[] vertices;

        public Vec2[] getVertices() {
            return vertices;
        }

        public void setVertices(Vec2[] value) {
            this.vertices = value;
        }

        private Color color;

        public Color getColor() {
            return color;
        }

        public void setColor(Color value) {
            this.color = value;
        }

        public Polygon(Vec2[] vertices, Color color) {
            this.vertices = vertices;
            this.color = color;
        }

        public static Polygon readFrom(InputStream stream) throws IOException {
            Vec2[] vertices;
            vertices = new Vec2[StreamUtil.readInt(stream)];
            for (int verticesIndex = 0; verticesIndex < vertices.length; verticesIndex++) {
                Vec2 verticesElement;
                verticesElement = Vec2.readFrom(stream);
                vertices[verticesIndex] = verticesElement;
            }
            Color color;
            color = Color.readFrom(stream);
            return new Polygon(vertices, color);
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
            StreamUtil.writeInt(stream, vertices.length);
            for (Vec2 verticesElement : vertices) {
                verticesElement.writeTo(stream);
            }
            color.writeTo(stream);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("Polygon { ");
            stringBuilder.append("vertices: ");
            stringBuilder.append("[ ");
            for (int verticesIndex = 0; verticesIndex < vertices.length; verticesIndex++) {
                if (verticesIndex != 0) {
                    stringBuilder.append(", ");
                }
                Vec2 verticesElement = vertices[verticesIndex];
                stringBuilder.append(String.valueOf(verticesElement));
            }
            stringBuilder.append(" ]");
            stringBuilder.append(", ");
            stringBuilder.append("color: ");
            stringBuilder.append(String.valueOf(color));
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class GradientPolygon extends DebugData {

        public static final int TAG = 8;

        private ColoredVertex[] vertices;

        public ColoredVertex[] getVertices() {
            return vertices;
        }

        public void setVertices(ColoredVertex[] value) {
            this.vertices = value;
        }

        public GradientPolygon(ColoredVertex[] vertices) {
            this.vertices = vertices;
        }

        public static GradientPolygon readFrom(InputStream stream) throws IOException {
            ColoredVertex[] vertices;
            vertices = new ColoredVertex[StreamUtil.readInt(stream)];
            for (int verticesIndex = 0; verticesIndex < vertices.length; verticesIndex++) {
                ColoredVertex verticesElement;
                verticesElement = ColoredVertex.readFrom(stream);
                vertices[verticesIndex] = verticesElement;
            }
            return new GradientPolygon(vertices);
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
            StreamUtil.writeInt(stream, vertices.length);
            for (ColoredVertex verticesElement : vertices) {
                verticesElement.writeTo(stream);
            }
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("GradientPolygon { ");
            stringBuilder.append("vertices: ");
            stringBuilder.append("[ ");
            for (int verticesIndex = 0; verticesIndex < vertices.length; verticesIndex++) {
                if (verticesIndex != 0) {
                    stringBuilder.append(", ");
                }
                ColoredVertex verticesElement = vertices[verticesIndex];
                stringBuilder.append(String.valueOf(verticesElement));
            }
            stringBuilder.append(" ]");
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class Segment extends DebugData {

        public static final int TAG = 9;

        private Vec2 firstEnd;

        public Vec2 getFirstEnd() {
            return firstEnd;
        }

        public void setFirstEnd(Vec2 value) {
            this.firstEnd = value;
        }

        private Vec2 secondEnd;

        public Vec2 getSecondEnd() {
            return secondEnd;
        }

        public void setSecondEnd(Vec2 value) {
            this.secondEnd = value;
        }

        private double width;

        public double getWidth() {
            return width;
        }

        public void setWidth(double value) {
            this.width = value;
        }

        private Color color;

        public Color getColor() {
            return color;
        }

        public void setColor(Color value) {
            this.color = value;
        }

        public Segment(Vec2 firstEnd, Vec2 secondEnd, double width, Color color) {
            this.firstEnd = firstEnd;
            this.secondEnd = secondEnd;
            this.width = width;
            this.color = color;
        }

        public static Segment readFrom(InputStream stream) throws IOException {
            Vec2 firstEnd;
            firstEnd = Vec2.readFrom(stream);
            Vec2 secondEnd;
            secondEnd = Vec2.readFrom(stream);
            double width;
            width = StreamUtil.readDouble(stream);
            Color color;
            color = Color.readFrom(stream);
            return new Segment(firstEnd, secondEnd, width, color);
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
            firstEnd.writeTo(stream);
            secondEnd.writeTo(stream);
            StreamUtil.writeDouble(stream, width);
            color.writeTo(stream);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("Segment { ");
            stringBuilder.append("firstEnd: ");
            stringBuilder.append(String.valueOf(firstEnd));
            stringBuilder.append(", ");
            stringBuilder.append("secondEnd: ");
            stringBuilder.append(String.valueOf(secondEnd));
            stringBuilder.append(", ");
            stringBuilder.append("width: ");
            stringBuilder.append(String.valueOf(width));
            stringBuilder.append(", ");
            stringBuilder.append("color: ");
            stringBuilder.append(String.valueOf(color));
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class GradientSegment extends DebugData {

        public static final int TAG = 10;

        private Vec2 firstEnd;

        public Vec2 getFirstEnd() {
            return firstEnd;
        }

        public void setFirstEnd(Vec2 value) {
            this.firstEnd = value;
        }

        private Color firstColor;

        public Color getFirstColor() {
            return firstColor;
        }

        public void setFirstColor(Color value) {
            this.firstColor = value;
        }

        private Vec2 secondEnd;

        public Vec2 getSecondEnd() {
            return secondEnd;
        }

        public void setSecondEnd(Vec2 value) {
            this.secondEnd = value;
        }

        private Color secondColor;

        public Color getSecondColor() {
            return secondColor;
        }

        public void setSecondColor(Color value) {
            this.secondColor = value;
        }

        private double width;

        public double getWidth() {
            return width;
        }

        public void setWidth(double value) {
            this.width = value;
        }

        public GradientSegment(Vec2 firstEnd, Color firstColor, Vec2 secondEnd, Color secondColor, double width) {
            this.firstEnd = firstEnd;
            this.firstColor = firstColor;
            this.secondEnd = secondEnd;
            this.secondColor = secondColor;
            this.width = width;
        }

        public static GradientSegment readFrom(InputStream stream) throws IOException {
            Vec2 firstEnd;
            firstEnd = Vec2.readFrom(stream);
            Color firstColor;
            firstColor = Color.readFrom(stream);
            Vec2 secondEnd;
            secondEnd = Vec2.readFrom(stream);
            Color secondColor;
            secondColor = Color.readFrom(stream);
            double width;
            width = StreamUtil.readDouble(stream);
            return new GradientSegment(firstEnd, firstColor, secondEnd, secondColor, width);
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
            firstEnd.writeTo(stream);
            firstColor.writeTo(stream);
            secondEnd.writeTo(stream);
            secondColor.writeTo(stream);
            StreamUtil.writeDouble(stream, width);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("GradientSegment { ");
            stringBuilder.append("firstEnd: ");
            stringBuilder.append(String.valueOf(firstEnd));
            stringBuilder.append(", ");
            stringBuilder.append("firstColor: ");
            stringBuilder.append(String.valueOf(firstColor));
            stringBuilder.append(", ");
            stringBuilder.append("secondEnd: ");
            stringBuilder.append(String.valueOf(secondEnd));
            stringBuilder.append(", ");
            stringBuilder.append("secondColor: ");
            stringBuilder.append(String.valueOf(secondColor));
            stringBuilder.append(", ");
            stringBuilder.append("width: ");
            stringBuilder.append(String.valueOf(width));
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class PolyLine extends DebugData {

        public static final int TAG = 11;

        private Vec2[] vertices;

        public Vec2[] getVertices() {
            return vertices;
        }

        public void setVertices(Vec2[] value) {
            this.vertices = value;
        }

        private double width;

        public double getWidth() {
            return width;
        }

        public void setWidth(double value) {
            this.width = value;
        }

        private Color color;

        public Color getColor() {
            return color;
        }

        public void setColor(Color value) {
            this.color = value;
        }

        public PolyLine(Vec2[] vertices, double width, Color color) {
            this.vertices = vertices;
            this.width = width;
            this.color = color;
        }

        public static PolyLine readFrom(InputStream stream) throws IOException {
            Vec2[] vertices;
            vertices = new Vec2[StreamUtil.readInt(stream)];
            for (int verticesIndex = 0; verticesIndex < vertices.length; verticesIndex++) {
                Vec2 verticesElement;
                verticesElement = Vec2.readFrom(stream);
                vertices[verticesIndex] = verticesElement;
            }
            double width;
            width = StreamUtil.readDouble(stream);
            Color color;
            color = Color.readFrom(stream);
            return new PolyLine(vertices, width, color);
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
            StreamUtil.writeInt(stream, vertices.length);
            for (Vec2 verticesElement : vertices) {
                verticesElement.writeTo(stream);
            }
            StreamUtil.writeDouble(stream, width);
            color.writeTo(stream);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("PolyLine { ");
            stringBuilder.append("vertices: ");
            stringBuilder.append("[ ");
            for (int verticesIndex = 0; verticesIndex < vertices.length; verticesIndex++) {
                if (verticesIndex != 0) {
                    stringBuilder.append(", ");
                }
                Vec2 verticesElement = vertices[verticesIndex];
                stringBuilder.append(String.valueOf(verticesElement));
            }
            stringBuilder.append(" ]");
            stringBuilder.append(", ");
            stringBuilder.append("width: ");
            stringBuilder.append(String.valueOf(width));
            stringBuilder.append(", ");
            stringBuilder.append("color: ");
            stringBuilder.append(String.valueOf(color));
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }

    public static class GradientPolyLine extends DebugData {

        public static final int TAG = 12;

        private ColoredVertex[] vertices;

        public ColoredVertex[] getVertices() {
            return vertices;
        }

        public void setVertices(ColoredVertex[] value) {
            this.vertices = value;
        }

        private double width;

        public double getWidth() {
            return width;
        }

        public void setWidth(double value) {
            this.width = value;
        }

        public GradientPolyLine(ColoredVertex[] vertices, double width) {
            this.vertices = vertices;
            this.width = width;
        }

        public static GradientPolyLine readFrom(InputStream stream) throws IOException {
            ColoredVertex[] vertices;
            vertices = new ColoredVertex[StreamUtil.readInt(stream)];
            for (int verticesIndex = 0; verticesIndex < vertices.length; verticesIndex++) {
                ColoredVertex verticesElement;
                verticesElement = ColoredVertex.readFrom(stream);
                vertices[verticesIndex] = verticesElement;
            }
            double width;
            width = StreamUtil.readDouble(stream);
            return new GradientPolyLine(vertices, width);
        }

        @Override
        public void writeTo(OutputStream stream) throws IOException {
            StreamUtil.writeInt(stream, TAG);
            StreamUtil.writeInt(stream, vertices.length);
            for (ColoredVertex verticesElement : vertices) {
                verticesElement.writeTo(stream);
            }
            StreamUtil.writeDouble(stream, width);
        }

        @Override
        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("GradientPolyLine { ");
            stringBuilder.append("vertices: ");
            stringBuilder.append("[ ");
            for (int verticesIndex = 0; verticesIndex < vertices.length; verticesIndex++) {
                if (verticesIndex != 0) {
                    stringBuilder.append(", ");
                }
                ColoredVertex verticesElement = vertices[verticesIndex];
                stringBuilder.append(String.valueOf(verticesElement));
            }
            stringBuilder.append(" ]");
            stringBuilder.append(", ");
            stringBuilder.append("width: ");
            stringBuilder.append(String.valueOf(width));
            stringBuilder.append(" }");
            return stringBuilder.toString();
        }
    }
}
