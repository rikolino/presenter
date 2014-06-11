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
    svgData.onclick = ((mouseEvent: dom.MouseEvent) => showElementWithId("rect3095"))
  }

  val actions = Array(PanAndZoom(null), FadeAndSlide(null, null))

  var currentAction = 0

  var currentX: js.Number = 0
  var currentY: js.Number = 0
  var timerFunction: Any = null

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
    val box = elem.getBoundingClientRect()
    svgData.setAttribute("viewBox", s"${box.left} ${box.top} ${box.width} ${box.height}")
  }
}

trait Action {
  def act(): Unit = ???
  def undo(): Unit = ???
}

case class PanAndZoom(toElement: dom.HTMLElement) extends Action
case class FadeAndSlide(oldElement: dom.HTMLElement, newElement: dom.Element) extends Action
