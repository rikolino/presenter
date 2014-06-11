package presenter

import scala.scalajs.js
import org.scalajs.dom
import js.annotation.JSExport

@JSExport
object Presentation {
  val svgObject = dom.document.getElementById("svg-object")
  val svgDocument = svgObject.asInstanceOf[js.Dynamic].contentDocument.asInstanceOf[dom.Document]
  val svgData = svgDocument.getElementById("svg2").asInstanceOf[dom.SVGElement]

  @JSExport
  def main(): Unit = {
    svgData.onmousedown = (action _)
    svgDocument.oncontextmenu = (mouseEvent: dom.MouseEvent) => false
  }

  val actions = Array(PanAndZoom("rect3095"), PanAndZoom("rect3095-1"), PanAndZoom("rect3095-1-7"), PanAndZoom("rect3095-1-7-1"), PanAndZoom("rect3095-7"))

  var currentAction = 0

  var currentX: js.Number = 0
  var currentY: js.Number = 0
  var timerFunction: Any = null

  def action(event: dom.MouseEvent) = {
    if (event.button == 0)
      nextAction()
    else if (event.button == 2)
      previousAction()
    false
  }

  def nextAction() = {
    actions(currentAction).act()
    currentAction += 1
    if (currentAction >= actions.length)
      currentAction = 0
  }

  def previousAction() = {
    if (currentAction <= 0)
      currentAction = actions.size -1
    else
      currentAction -= 1
    actions(currentAction-1).act()
  }

  def startAnimation() = {
    currentX = 200
    currentY = 200
    if (timerFunction == null) {
      timerFunction = dom.setInterval(() =>
        moveViewBox(200, 200, 150, 150), 10)
    }
  }

  def moveViewBox(fromX: js.Number, fromY: js.Number, toX: js.Number, toY: js.Number) = {
    if (currentX > toX || currentY > toY) {
      currentX = currentX - 1
      currentY = currentY - 1
      svgData.setAttribute("viewBox", "" + currentX + " " + currentY + " 1920 1200")
    }
  }

  def showElementWithId(id: String) = {
    val elem = svgDocument.getElementById(id)
    if (elem == null) dom.alert(s"no elem with id $id!")
    svgData.setAttribute("viewBox", s"0 0 1024 768")
    val box = elem.getBoundingClientRect()
    //dom.alert(s"${box.left} ${box.top} ${box.width} ${box.height}")
    svgData.setAttribute("viewBox", s"${box.left} ${box.top} ${box.width} ${box.height}")
  }

  trait Action {
    def act(): Unit
    def undo(): Unit = ???
  }

  case class PanAndZoom(id: String) extends Action {
    def act(): Unit = showElementWithId(id)
  }
  case class FadeAndSlide(oldElement: dom.HTMLElement, newElement: dom.Element) extends Action {
    def act(): Unit = ???
  }

}




