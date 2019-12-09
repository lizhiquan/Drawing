# Drawing

In the main class, there are 2 components: `MyToolBar` and `CanvasPane`.

- `MyToolBar` has the reference of CanvasPane. When a user selects a shape on the toolbar
or the selection feature, it'll update the corresponding handler based on the currently
selected button.
- `CanvasPane` has a list of shapes to keep track and some methods like `clear()` and `update()`
to refresh the canvas with new shapes.

There are 2 types of event handler, and both of them implement a common interface called
`EventResponsible`:

- `DrawingTool`: handles drawing a shape. This class will forward drawing events to a
specific shape to handle. After the user has done drawing, the shape will notify back to
this class to add a new shape to the canvas and tell the canvas to update.
- `SelectionTool`: handles selecting a shape as well as moving and deleting a selected shape.

## How to delete a shape?

- Select the `Select` option on the toolbar.
- Click on the shape to select it.
- Hit `Backspace` or `Delete`.

## How to draw a polygon?

- Select the `Polygon` option on the toolbar.
- Continue to click on the canvas to add a vertex to the polygon. The polygon only shows up
on the screen when there are more than 2 vertices (2 clicks).
- To draw a new polygon after you have done drawing a polygon, press `Enter`.
