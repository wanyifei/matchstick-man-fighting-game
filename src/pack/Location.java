package pack;

/**
 * YOU MAY NOT MODIFY THIS FILE.<br>
 * <p/>
 * Immutable class that represents (x,y) coordinates. It also has some static
 * convenience methods performing
 * calculations.<br> This class overrides the toString() method for
 * convenience.
 */
public class Location
{
  /**
   * Tolerance for equality testing on coordinates.
   */
  private static final double TOLERANCE = 0.00001;
  private double x;
  private double y;

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public Location( double inX, double inY )
  {
    x = inX;
    y = inY;
  }

  // ---------------------------------------------------------------------------

  /**
   * For a single clock tick, calculates the next location between 'start'
   * and
   * 'end' given 'speed' (which is in
   * distance per clock tick).
   */
  public static Location calculateNextLocation( Location start, Location end,
                                                double speed )
  {
    Location unitVector = calculateUnitVector(start, end);
    return new Location(start.x + unitVector.x * speed,
                        start.y + unitVector.y * speed);
  }

  // ---------------------------------------------------------------------------

  public static double getDistanceBetween( Location a, Location b )
  {
    return Math.sqrt(Math.pow(b.x - a.x, 2.0) + Math.pow(b.y - a.y, 2.0));
  }

  // ---------------------------------------------------------------------------

  /**
   * Compares the x and y coordinates within a tolerance of {@link
   * Location#TOLERANCE}.
   */
  @Override
  public boolean equals( Object obj )
  {
    if ( this == obj )
    {
      return true;
    }
    if ( obj == null )
    {
      return false;
    }
    if ( !(obj instanceof Location) )
    {
      return false;
    }
    Location other = ( Location ) obj;

    return x - other.x <= TOLERANCE && y - other.y <= TOLERANCE;

  }// equals

  // ---------------------------------------------------------------------------

  /**
   * NOTE: The fractional part of the coordinates is displayed to a precision
   * of 5 digits.
   */
  public String toString()
  {
    return String.format("(%.5f, %.5f)", x, y);
  }

  // ---------------------------------------------------------------------------

  public double getX()
  {
    return x;
  }

  // ---------------------------------------------------------------------------

  public double getY()
  {
    return y;
  }

  // ---------------------------------------------------------------------------

  /**
   * Calculate the unit vector from one point in the direction of another.
   * Return the result as a Location object,
   * where the x and y values are actually the x and y magnitudes.
   */
  private static Location calculateUnitVector( Location start, Location end )
  {
    double distance = getDistanceBetween(start, end);
    Location vector = new Location(end.x - start.x, end.y - start.y);
    vector.x /= distance;
    vector.y /= distance;

    return vector;
  }


  // ---------------------------------------------------------------------------

}// class
