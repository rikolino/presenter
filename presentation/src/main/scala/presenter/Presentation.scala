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
    val slideNum = util.Try(dom.window.location.hash.drop(1).toInt).getOrElse(0)
    var i = slideNum
    while (i > 0) {
      nextAction()
      i -= 1

    }

  }

  val actions = Array(
    PanAndZoom("rect3095"),
    PanAndZoom("rect3095-3"),
    PanAndZoom("rect3095-1"),
    PanAndZoom("rect3095-1-7"),
    PanAndZoom("svg2"),

    //PanAndZoom("rect3095-1-7"), // Unit Insertion
    FadeAndSlide("g3553", "g3561", 1),
    FadeAndSlide("g3561", "g3569", 2),
    FadeAndSlide("g3569", "g3577", 3),

    //PanAndZoom("rect3095-1-7-1"), // Procedures
    FadeAndSlide("g3609", "g3601", 1),
    FadeAndSlide("g3601", "g3593", 2),
    FadeAndSlide("g3593", "g3585", 3),

    //PanAndZoom("rect3095-1-7-1-7"), // Constant Inlining
    FadeAndSlide("g3333", "g3338", 1),
    FadeAndSlide("g3338", "g3343", 2),
    FadeAndSlide("g3343", "g3348", 3),

    //PanAndZoom("rect3095-1-7-1-7-4"), // Lossy Implicits
    FadeAndSlide("g3424", "g3432", 1),
    FadeAndSlide("g3432", "g3440", 2),
    FadeAndSlide("g3440", "g3448", 3),

    //PanAndZoom("rect3095-1-7-1-7-6"), // Avian
    FadeAndSlide("g3456", "g3464", 1),
    FadeAndSlide("g3464", "g3472", 2),
    FadeAndSlide("g3472", "g3480", 3),

    PanAndZoom("rect3095-7"),
    PanAndZoom("rect3095-7-6"),
    PanAndZoom("svg2"))

  var currentAction = 0

  var currentX: js.Number = 0
  var currentY: js.Number = 0
  var timerFunction: Any = null

  def action(event: dom.MouseEvent) = {
    if (event.button == 0)
      nextAction()
    else if (event.button == 2)
      previousAction()

    dom.window.history.pushState(null, null, s"#$currentAction")
    false
  }

  def nextAction() = {
    actions(currentAction).act()
    currentAction += 1
    if (currentAction >= actions.length)
      currentAction = 0
  }

  def previousAction() = {
    if (currentAction < 1)
      currentAction = actions.size - 1
    else
      currentAction -= 1
    actions(currentAction - 1).act()
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
    svgData.setAttribute("viewBox", s"${box.left} ${box.top} ${box.width} ${box.height}")
  }

  trait Action {
    def act(): Unit
    def undo(): Unit = ???
  }

  case class PanAndZoom(id: String) extends Action {
    def act(): Unit = showElementWithId(id)
  }

  case class FadeAndSlide(oldElementId: String, newElementId: String, numberOfSlide: Int) extends Action {
    def act(): Unit = {
      val oldElement = svgDocument.getElementById(oldElementId)
      val newElement = svgDocument.getElementById(newElementId)
      val oldElementMove = numberOfSlide * -90
      val newElementMove = numberOfSlide * -70

      //oldElement.setAttribute("transform", s" translate(0,$oldElementMove)")
      oldElement.setAttribute("visibility", "hidden");
      newElement.setAttribute("transform", s" translate(0,$newElementMove)")
    }
  }
}
