package me.wobblyyyy.pathfinder.fieldMapping.shapes;

import me.wobblyyyy.intra.ftc2.utils.jrep.ListWrapper;
import me.wobblyyyy.intra.ftc2.utils.math.Math;
import me.wobblyyyy.pathfinder.fieldMapping.components.Component;
import me.wobblyyyy.pathfinder.fieldMapping.components.Coordinate;
import me.wobblyyyy.pathfinder.fieldMapping.components.countable.Line;

import java.util.ArrayList;

/**
 * The most basic of the geometric shapes used in
 * the field mapping portion of this code.
 *
 * <p>
 * The four lines; top, right, bottom, and left, are
 * used for collision detection. Mostly everything else
 * in this class is fairly useless.
 * </p>
 * <p>
 * The following words are used frequently throughout this class.
 * Some of those words have the same meaning.
 * <uL>
 * <li>TOP (or FRONT)</li>
 * <li>RIGHT</li>
 * <li>BOTTOM (or BACK)</li>
 * <li>LEFT</li>
 * </uL>
 * In case you have not yet noticed, all of these words are relative
 * words - meaning that they're relative to whatever angle the rectangle
 * is being viewed at. Rather than further complicating the way in which
 * this library's geometry system works, I decided to stick with (quite
 * simple) relative words. This does mean, however, that you'll need to
 * keep track of which side is which somehow. I don't care how you do it,
 * or if you even do it at all - but it might be at least a little bit
 * helpful to have/do.
 * </p>
 *
 * @author Colin Robertson
 */
public class Rectangle implements Shape {
    /**
     * The front-right corner of the rectangle.
     * This is relative to the origin + rotation.
     */
    public final Coordinate<Double> frontRight;

    /**
     * The back-right corner of the rectangle.
     * This is relative to the origin + rotation.
     */
    public final Coordinate<Double> backRight;

    /**
     * The front-left corner of the rectangle.
     * This is relative to the origin + rotation.
     */
    public final Coordinate<Double> frontLeft;

    /**
     * The back-left corner of the rectangle.
     * This is relative to the origin + rotation.
     */
    public final Coordinate<Double> backLeft;

    /**
     * The center of the rectangle.
     * This is relative to the origin + rotation.
     */
    public final Coordinate<Double> center;

    /**
     * The top of the rectangle.
     * This is relative to the origin + rotation.
     */
    public final Line top;

    /**
     * The right of the rectangle.
     * This is relative to the origin + rotation.
     */
    public final Line right;

    /**
     * The bottom of the rectangle.
     * This is relative to the origin + rotation.
     */
    public final Line bottom;

    /**
     * The left of the triangle.
     * This is relative to the origin + rotation.
     */
    public final Line left;

    /**
     * Which corner the rectangle should be drawn from. This is NOT
     * always the same corner which the rectangle will be rotated from,
     * however - just the corner it'll be drawn from. X and Y are relative
     * to this corner, meaning top right's Y draw would have a different
     * impact than bottom left's Y draw - one (top right) would be negative,
     * and the other (bottom left) would be positive.
     */
    public final Corners drawCorner;

    /**
     * The corner which the rectangle will be rotated from. You don't need
     * to rotate the rectangle, by the way - it's an entirely optional step.
     * If you don't want to rotate the rectangle, you can use any corner
     * (CENTER or maybe your drawCorner) as the corner of rotation, and set
     * the rotation angle to 0, representing a net change of zero rotation.
     */
    public final Corners rotateCorner;

    /**
     * The coordinate where the shape will be drawn from. This point is
     * directly correlative to drawCorner - having a drawCorner of top right
     * means that this code will interpret the starting point as the top right
     * corner of the rectangle, and thus draw the rectangle as so. Make sure that
     * this is the correct corner. Assuming you're familiar with something such as
     * JavaScript's "canvas" functionality, you will (most often) want to use the
     * top left corner as a starting point and figure out the coordinate of
     * said corner.
     */
    public final Coordinate<Double> startingPoint;

    /**
     * How far, in the X dimension, the rectangle should be drawn. Note that this
     * is relative to which corner the rectangle is being drawn from. Having a draw
     * corner of top left means that both X and Y draws are negative.
     */
    public final double xDraw;

    /**
     * How far, in the Y dimension, the rectangle should be drawn. Note that this
     * is relative to which corner the rectangle is being drawn from. Having a draw
     * corner of the top left means that both X and Y draws are negative.
     */
    public final double yDraw;

    /**
     * The angle at which the entire rectangle should be rotate from. I believe that
     * this angle is in radians, and any code you write using this angle should reflect
     * that. If you have an angle which is in degrees, Java's native math class should
     * include a function for converting degrees to radians.
     */
    public final double rotationalAngle;

    /**
     * "Is collidable exterior."
     */
    private boolean _ICE = false;

    /**
     * "Is collidable interior."
     */
    private boolean _ICI = false;

    /**
     * The one and only... rectangle constructor!
     *
     * @param drawCorner           which corner the rectangle should be drawn from. This is NOT
     *                             always the same corner which the rectangle will be rotated from,
     *                             however - just the corner it'll be drawn from. X and Y are relative
     *                             to this corner, meaning top right's Y draw would have a different
     *                             impact than bottom left's Y draw - one (top right) would be negative,
     *                             and the other (bottom left) would be positive.
     * @param rotateCorner         the corner which the rectangle will be rotated from. You don't need
     *                             to rotate the rectangle, by the way - it's an entirely optional step.
     *                             If you don't want to rotate the rectangle, you can use any corner
     *                             (CENTER or maybe your drawCorner) as the corner of rotation, and set
     *                             the rotation angle to 0, representing a net change of zero rotation.
     * @param startingPoint        the coordinate where the shape will be drawn from. This point is
     *                             directly correlative to drawCorner - having a drawCorner of top right
     *                             means that this code will interpret the starting point as the top right
     *                             corner of the rectangle, and thus draw the rectangle as so. Make sure that
     *                             this is the correct corner. Assuming you're familiar with something such as
     *                             JavaScript's "canvas" functionality, you will (most often) want to use the
     *                             top left corner as a starting point and figure out the coordinate of
     *                             said corner.
     * @param xDraw                how far, in the X dimension, the rectangle should be drawn. Note that this
     *                             is relative to which corner the rectangle is being drawn from. Having a draw
     *                             corner of top left means that both X and Y draws are negative.
     * @param yDraw                how far, in the Y dimension, the rectangle should be drawn. Note that this
     *                             is relative to which corner the rectangle is being drawn from. Having a draw
     *                             corner of the top left means that both X and Y draws are negative.
     * @param rotationalAngle      the angle at which the entire rectangle should be rotate from. I believe that
     *                             this angle is in radians, and any code you write using this angle should reflect
     *                             that. If you have an angle which is in degrees, Java's native math class should
     *                             include a function for converting degrees to radians.
     * @param isCollidableExterior whether or not the exterior of the rectangle is collidable. A collidable shape
     *                             is recognized by the collision detection system, and the robot will intentionally
     *                             not steer right into an exterior collidable object.
     * @param isCollidableInterior whether or not the robot can still move while inside a shape. For example, the main
     *                             field of the FTC challenge has a non-collidable interior, meaning the robot can
     *                             still move while inside of the shape / zone.
     */
    public Rectangle(Corners drawCorner,
                     Corners rotateCorner,
                     Coordinate<Double> startingPoint,
                     double xDraw,
                     double yDraw,
                     double rotationalAngle,
                     boolean isCollidableExterior,
                     boolean isCollidableInterior) {
        // xfr = x, front right
        // xfl = x, front left
        // xbr = x, back right
        // xbl = x, back left
        // yfr = y, front right
        // yfl = y, front left
        // ybr = y, back right
        // ybl = y, back left
        double xfr, xfl, xbr, xbl, yfr, yfl, ybr, ybl;
        double x = startingPoint.getX();
        double y = startingPoint.getY();
        switch (drawCorner) {
            case FRONT_RIGHT:
                xfr = x;
                yfr = y;
                xfl = x - xDraw;
                yfl = y;
                xbr = x;
                ybr = y - yDraw;
                xbl = x - xDraw;
                ybl = y - yDraw;
                break;
            case FRONT_LEFT:
                xfr = x + xDraw;
                yfr = y;
                xfl = x;
                yfl = y;
                xbr = x + xDraw;
                ybr = y - yDraw;
                xbl = x;
                ybl = y - yDraw;
                break;
            case BACK_RIGHT:
                xfr = x;
                yfr = y + yDraw;
                xfl = x - xDraw;
                yfl = y + yDraw;
                xbr = x;
                ybr = y;
                xbl = x - xDraw;
                ybl = y;
                break;
            case BACK_LEFT:
                xfr = x + xDraw;
                yfr = y + yDraw;
                xfl = x;
                yfl = y + yDraw;
                xbr = x + xDraw;
                ybr = y;
                xbl = x;
                ybl = y;
                break;
            default:
                throw new IllegalArgumentException("Invalid corner!");
        }
        // The order of coordinates is as follows. It's consistent with
        // all of the other code in this section...
        // 1. Front Right
        // 2. Front Left
        // 3. Back Right
        // 4. Back Left
        // Using array rotations, we can get a grand total of four
        // different possible orders, which are as follows...
        // 1. fr, fl, br, bl (zero rotation) (start)
        // 2. bl, fr, fl, br (one rotations)
        // 3. br, bl, fr, fl (two rotations)
        // 4. fl, br, bl, fr (three rotations)
        // 5. fr, fl, br, bl (four rotations) (back to base)
        // For each of those combinations (1, 2, 3, and 4), everything
        // is rotated around the first point, which is, in order...
        // Front right, back left, back right, front left.
        // And finally, in order to rotate everything back until
        // we've completed a full loop and everything is in the
        // correct position, we have to counter each and every one of
        // any of the potential rotations as so:
        // 1 rotation needs 3 more rotations.
        // 2 rotations needs 2 more rotations.
        // 3 rotations needs 1 more rotation.
        // 4 rotations is how many we need to complete a full loop.
        Coordinate<Double>[] positions = new Coordinate[]{
                new Coordinate<>(xfr, yfr),
                new Coordinate<>(xfl, yfl),
                new Coordinate<>(xbr, ybr),
                new Coordinate<>(xbl, ybl)
        };
        Coordinate<Double> midpoint = new Coordinate<>(
                Math.average(positions[0].getX(), positions[3].getX()),
                Math.average(positions[0].getY(), positions[3].getY())
        );
        if (rotateCorner != Corners.CENTER) {
            throw new IllegalArgumentException(
                    "Please only use 'CENTER' as a draw corner parameter. Other " +
                            "options as draw corners are currently not supported. " +
                            "If you'd like to rotate from another corner, go to the " +
                            "Rectangle.java file in the Pathfinder library to see " +
                            "some of the math that you'll need. :)"
            );
        }
        // Front right is still the first position.
        // We don't need to rotate this back, as it stays in
        // our so-called "default" position the whole entire
        // time.
        // The array has been rotated once, making the
        // first position back left.
        // Needs 3 rotations to go back.
        // Two rotations - back right.
        // Needs two more rotations to go back.
        // Finally, front left.
        // Needs just a single rotation to get back to normal.
        positions[0] = rotatePoint(midpoint, positions[0], rotationalAngle);
        positions[1] = rotatePoint(midpoint, positions[1], rotationalAngle);
        positions[2] = rotatePoint(midpoint, positions[2], rotationalAngle);
        positions[3] = rotatePoint(midpoint, positions[3], rotationalAngle);
        // Now that all of our coordinates are back in the same place, we
        // have to set all of the final variables at the top part of this
        // file - most notably lines, so we can work with collision detection
        // later on when we get to more complex field mapping.
        frontRight = positions[0];
        frontLeft = positions[1];
        backRight = positions[2];
        backLeft = positions[3];
        center = midpoint;
        // We still have one very important thing to do here - you guessed it...
        // Coming up with lines! Luckily for us, all four of those lines can
        // be defined as so.
        // |-----------|-----------------|
        // | LINE NAME | REQUIRED POINTS |
        // |-----------|-----------------|
        // | TOP       | FL, FR          |
        // | RIGHT     | FR, BR          |
        // | BOTTOM    | BR, BL          |
        // | LEFT      | BL, FL          |
        // |-----------|-----------------|
        top = new Line(frontLeft, frontRight);
        right = new Line(frontRight, backRight);
        bottom = new Line(backRight, backLeft);
        left = new Line(backLeft, frontLeft);

        _ICE = isCollidableExterior;
        _ICI = isCollidableInterior;

        this.drawCorner = drawCorner;
        this.rotateCorner = rotateCorner;
        this.startingPoint = startingPoint;
        this.xDraw = xDraw;
        this.yDraw = yDraw;
        this.rotationalAngle = rotationalAngle;
    }

    public boolean isCollidableExterior() {
        return _ICE;
    }

    public boolean isCollidableInterior() {
        return _ICI;
    }

    /**
     * Rotate a point around another point by a specified angle,
     * notated in radians. (Or at least I think so.)
     *
     * @param center the center point to rotate around - in other words,
     *               this is the "axis" or "origin."
     * @param point  the point which should be rotated around the center.
     * @param angle  the angle, in radians, depicting how far the point
     *               should be rotated around the center point.
     * @return the freshly rotated point.
     */
    public Coordinate<Double> rotatePoint(Coordinate<Double> center,
                                          Coordinate<Double> point,
                                          double angle) {
        double c_x = center.getX();
        double c_y = center.getY();
        double p_x = point.getX();
        double p_y = point.getY();
        // Apply the rotation matrix or whatever the FUCK it's called.
        // Rv = [ cos(theta), -sin(theta) ] * [ x ] = [ x cos(theta) - y sin(theta) ]
        //      [ sin(theta),  cos(theta) ]   [ y ]   [ x sin(theta) + y cos(theta) ]
        // x prime = x cos(theta) - y sin(theta)
        // y prime = x sin(theta) + y cos(theta)
        double x1 = p_x - c_x;
        double y1 = p_y - c_y;
        double x2 = x1 * Math.cos(angle) - y1 * Math.sin(angle);
        double y2 = x1 * Math.sin(angle) + y1 * Math.cos(angle);
        return new Coordinate<>(
                x2 + center.getX(),
                y2 + center.getY()
        );
    }

    /**
     * A privately and internally used method for rotating three points around a fourth.
     *
     * @param points         the array of points to use.
     * @param rotationFactor how far the points should be rotated.
     * @return a freshly rotated array.
     */
    private Coordinate<Double>[] rotateAllPointsAroundFirstPoint(Coordinate<Double>[] points,
                                                                 double rotationFactor) {
        return new Coordinate[]{
                points[0],
                rotatePoint(points[0], points[1], rotationFactor),
                rotatePoint(points[0], points[2], rotationFactor),
                rotatePoint(points[0], points[3], rotationFactor)
        };
    }

    /**
     * Rotate an array. This doesn't mean rotate them geometrically, or
     * relative to another point on a cartesian coordinate plane. Rather,
     * this literally just means to rotate the array as if it's an
     * infinitely scrolling array, which Java obviously doesn't have.
     *
     * @param arr  the array
     * @param reps how many times it should be rotated
     * @return a rotated array
     */
    private Coordinate<Double>[] rotateArray(Coordinate<Double>[] arr,
                                             int reps) {
        if (reps > 3 || reps < 0) return null;
        while (reps > 0) {
            Coordinate<Double>[] newArr = new Coordinate[]{
                    arr[3],
                    arr[0],
                    arr[1],
                    arr[2]
            };
            arr = newArr;
            reps--;
        }
        return arr;
    }

    /**
     * Used to determine whether a given point is inside of the rectangle.
     *
     * <p>
     * This functions by finding the maximums of all of the X and Y positions
     * of our rectangle and ensuring that the given point fits in between the
     * range of the aforementioned maximums and the later-calculated minimums.
     * </p>
     *
     * @param point the point to check
     * @return whether or not the point is inside the rectangle
     */
    public boolean isPointInShape(Coordinate<Double> point) {
        double p_x = point.getX(); // the test point's x value
        double p_y = point.getY(); // the test point's y value
        // Find the highest X value used in any of the rectangle's
        // four corners.
        double maxX = Math.max(
                Math.max(frontRight.getX(), frontLeft.getX()),
                Math.max(backRight.getX(), backLeft.getX())
        );
        // Find the lowest X value of any of the rectangle's
        // four corners.
        double minX = Math.min(
                Math.min(frontRight.getX(), frontLeft.getX()),
                Math.min(backRight.getX(), backLeft.getX())
        );
        // Find the highest Y value used in any of the rectangle's
        // four corners.
        double maxY = Math.max(
                Math.max(frontRight.getY(), frontLeft.getY()),
                Math.max(backRight.getY(), backLeft.getY())
        );
        // Find the lowest Y value used in any of the rectangle's
        // four corners.
        double minY = Math.min(
                Math.min(frontRight.getY(), frontLeft.getY()),
                Math.min(backRight.getY(), backLeft.getY())
        );
        // These two booleans aren't really needed, but they help
        // clean up the code a little bit and make it easier to
        // interpret what's going on here.
        // xValid and yValid have to do with, obviously, the validity
        // of a point's positioning. An invalid position would be, for
        // example, one which is outside of the rectangle. Any position
        // that is inside of the rectangle is considered valid.
        boolean xValid = minX <= p_x && p_x <= maxX;
        boolean yValid = minY <= p_y && p_y <= maxY;
//        double longestLineLength = Math.max(
//                new Line(center, top.midpoint).length,
//                new Line(center, right.midpoint).length
//        );
//        boolean withinRange = new Line(center, point).length <= longestLineLength;
//        boolean withinRange = new Line(center, point).length <= new Line(center, frontLeft).length;
        // Return whether or not the point's X and Y values are both
        // valid and inside the rectangle. If either of them are not,
        // this will return false, indicating that the given point
        // was not inside of the rectangle.
        return xValid && yValid;
    }

    /**
     * Check whether or not a given rectangle enters this rectangle.
     *
     * <p>
     * This might need optimization in the future. I'm not really sure
     * how powerful the control hub is or how many calculations per second
     * it can do or whatever metric you'd like to use, but for now I'm just
     * going to leave this as it is and hope it works.
     * </p>
     *
     * @param rectangle the rectangle to check.
     * @return whether the two rectangles intersect.
     */
    public boolean doesRectangleIntersect(Rectangle rectangle) {
        Line top = rectangle.top;
        Line right = rectangle.right;
        Line bottom = rectangle.bottom;
        Line left = rectangle.left;
        return
                doesLineEnterShape(top) ||
                        doesLineEnterShape(right) ||
                        doesLineEnterShape(bottom) ||
                        doesLineEnterShape(left);
    }

    /**
     * Check whether or not a given line at any point enters our rectangle.
     *
     * @param line the line to check.
     * @return whether that line enters the rectangle.
     */
    public boolean doesLineEnterShape(Line line) {
        return
                Line.doesIntersect(line, top) ||
                        Line.doesIntersect(line, right) ||
                        Line.doesIntersect(line, bottom) ||
                        Line.doesIntersect(line, left);
    }

    /**
     * Get a count of how many components (lines) are in the rectangle.
     *
     * <p>
     * This will obviously always be four - rectangles can only have
     * four lines.
     * </p>
     *
     * @return how many lines there are.
     */
    public int getComponentCount() {
        return 4;
    }

    /**
     * Get a ListWrapper containing all of the components (lines) in the rectangle.
     *
     * @return a list of all the lines.
     */
    public ListWrapper<Component> getComponents() {
        return new ListWrapper<>(
                new ArrayList<Component>() {{
                    add(top);
                    add(right);
                    add(bottom);
                    add(left);
                }}
        );
    }

    /**
     * A list of different positions on a rectangle.
     *
     * <p>
     * A more apt name would certainly be "positions." You can't tell me with a straight face
     * that you could seriously consider "center" to be one of the FOUR corners of a rectangle.
     * Should we consider refactoring this in the future?
     * </p>
     */
    public enum Corners {
        FRONT_RIGHT,
        BACK_RIGHT,
        FRONT_LEFT,
        BACK_LEFT,
        CENTER
    }

    /**
     * I'm writing this comment so the file is 500 lines long. That's all.
     *
     * @return the center point of the shape
     */
    @Override
    public Coordinate<Double> getCenterPoint() {
        Line midline = new Line(backLeft, frontRight);
        return new Coordinate<>(
                midline.midpoint.getX(),
                midline.midpoint.getY()
        );
    }
}
