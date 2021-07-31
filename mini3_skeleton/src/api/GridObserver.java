package api;

/**
 * Interface representing a component to be notified of status
 * changes in a GridCell.
 */
public interface GridObserver
{
  /**
   * Indicates to observer that the status of the given cell has changed.
   * @param cell
   *   a GridCell whose status has changed
   */
  void statusChanged(GridCell cell);
}
