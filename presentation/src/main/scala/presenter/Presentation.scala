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
    PanAndZoom("ARROW").act()
    val slideNum = util.Try(dom.window.location.hash.drop(1).toInt).getOrElse(0)
    var i = slideNum
    while (i > 0) {
      nextAction()
      i -= 1

    }

  }

  val actions = Array(
    PanAndZoom("ARROW"),
    PanAndZoom("TITLE"),
    PanAndZoom("WELCOME"),
    PanAndZoom("ME"),
    PanAndZoom("GOAL"),
    PanAndZoom("MOTIVATION"),
    PanAndZoom("FALLACIES"),
    PanAndZoom("APPROACH"),
    PanAndZoom("DISCLAIMER"),

    PanAndZoom("PAST"),
    PanAndZoom("ARROW"),

    PanAndZoom("PASTPRESENT"),

    PanAndZoom("UI"),
    FadeAndSlideDown("UI-0", "UI-1", 1),
    FadeAndSlideDown("UI-1", "UI-2", 2),
    FadeAndSlideDown("UI-2", "UI-3", 3),
    FadeAndSlideDown("UI-3", "UI-4", 4),
    FadeAndSlideDown("UI-4", "UI-5", 5),

    PanAndZoom("PASTPRESENT"),

    PanAndZoom("FP"),
    FadeAndSlideDown("FP-0", "FP-1", 1),
    FadeAndSlideDown("FP-1", "FP-2", 2),
    FadeAndSlideDown("FP-2", "FP-3", 3),
    FadeAndSlideDown("FP-3", "FP-4", 4),
    FadeAndSlideDown("FP-4", "FP-5", 5),

    PanAndZoom("PASTPRESENT"),

    PanAndZoom("OL"),
    FadeAndSlide("OL-0", "OL-1", 1),
    FadeAndSlide("OL-1", "OL-2", 2),
    FadeAndSlide("OL-2", "OL-3", 3),
    FadeAndSlide("OL-3", "OL-4", 4),

    PanAndZoom("PASTARROW"),

    PanAndZoom("FC"),
    FadeAndSlide("FC-0", "FC-1", 1),
    FadeAndSlide("FC-1", "FC-2", 2),

    PanAndZoom("PRESENT"),

    PanAndZoom("PASTPRESENT"),

    PanAndZoom("PS"),
    FadeAndSlideDown("PS-0", "PS-1", 1),
    FadeAndSlideDown("PS-1", "PS-2", 2),
    FadeAndSlideDown("PS-2", "PS-3", 3),
    FadeAndSlideDown("PS-3", "PS-4", 4),
    FadeAndSlideDown("PS-4", "PS-5", 5),
    FadeAndSlideDown("PS-5", "PS-6", 6),

    PanAndZoom("PASTPRESENT"),

    PanAndZoom("VB"),
    FadeAndSlide("VB-0", "VB-1", 1),
    FadeAndSlide("VB-1", "VB-2", 2),
    FadeAndSlide("VB-2", "VB-3", 3),
    FadeAndSlide("VB-3", "VB-4", 4),
    FadeAndSlide("VB-4", "VB-5", 5),

    PanAndZoom("FUTURE"),

    PanAndZoom("TP"),
    FadeAndSlide("TP-0", "TP-1", 1),
    FadeAndSlide("TP-1", "TP-2", 2),
    FadeAndSlide("TP-2", "TP-3", 3),

    PanAndZoom("LI"),
    FadeAndSlide("LI-0", "LI-1", 1),
    FadeAndSlide("LI-1", "LI-2", 2),

    PanAndZoom("EN"),
    FadeAndSlide("EN-0", "EN-1", 1),
    FadeAndSlide("EN-1", "EN-2", 2),

    PanAndZoom("DI"),
    FadeAndSlide("DI-0", "DI-1", 1),
    FadeAndSlide("DI-1", "DI-2", 2),
    FadeAndSlide("DI-2", "DI-3", 3),

    PanAndZoom("AV"),
    FadeAndSlide("AV-0", "AV-1", 1),
    FadeAndSlide("AV-1", "AV-2", 2),
    FadeAndSlide("AV-2", "AV-3", 3),
    PanAndZoom("MO"),

    PanAndZoom("CONCLUSION"),

    PanAndZoom("THANKS"),
    PanAndZoom("QUESTIONS"),
    PanAndZoom("END"),
    PanAndZoom("ARROW"))

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

  /*
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
  */

  def showElementWithId(id: String) = {
    val elem = svgDocument.getElementById(id)
    if (elem == null) dom.alert(s"no elem with id $id!")
    svgData.setAttribute("viewBox", s"0 0 1024 768")
    val box = elem.getBoundingClientRect()
    svgData.setAttribute("viewBox", s"${box.left} ${box.top} ${box.width} ${box.height}")
  }

  trait Action {
    def act(): Unit
  }

  case class PanAndZoom(id: String) extends Action {
    def act(): Unit = showElementWithId(id)
  }

  case class FadeAndSlide(oldElementId: String, newElementId: String, numberOfSlide: Int) extends Action {
    def act(): Unit = {
      val oldElement = svgDocument.getElementById(oldElementId)
      if (oldElement == null) dom.alert(s"no elem with id $oldElementId!")
      val newElement = svgDocument.getElementById(newElementId)
      if (newElement == null) dom.alert(s"no elem with id $newElementId!")
      //val oldElementMove = numberOfSlide * -90
      val newElementMove = numberOfSlide * -70

      //oldElement.setAttribute("transform", s" translate(0,$oldElementMove)")
      oldElement.setAttribute("visibility", "hidden");
      newElement.setAttribute("transform", s" translate(0,$newElementMove)")
    }
  }

  case class FadeAndSlideDown(oldElementId: String, newElementId: String, numberOfSlide: Int) extends Action {
    def act(): Unit = {
      val oldElement = svgDocument.getElementById(oldElementId)
      if (oldElement == null) dom.alert(s"no elem with id $oldElementId!")
      val newElement = svgDocument.getElementById(newElementId)
      if (newElement == null) dom.alert(s"no elem with id $newElementId!")
      //val oldElementMove = numberOfSlide * -90
      val newElementMove = (numberOfSlide * 70) + 20

      //oldElement.setAttribute("transform", s" translate(0,$oldElementMove)")
      oldElement.setAttribute("visibility", "hidden");
      newElement.setAttribute("transform", s" translate(0,$newElementMove)")
    }
  }
}
