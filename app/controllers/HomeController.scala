package controllers

import com.google.inject.Inject
import play.api.mvc.{AbstractController, Action, Controller, ControllerComponents}

/**
  * A very small controller that renders a home page.
  */
class HomeController @Inject()(cc: ControllerComponents) (implicit assetsFinder: AssetsFinder)
  extends AbstractController(cc)  {

  def index = Action { implicit request =>
    Ok(views.html.index())
  }
}
