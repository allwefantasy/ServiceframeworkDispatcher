package serviceframework.dispatcher.default

import java.util

import net.csdn.common.logging.Loggers
import serviceframework.dispatcher.{Compositor, Processor, Strategy}

/**
 * 4/19/16 WilliamZhu(allwefantasy@gmail.com)
 */
class LinearStrategy[T] extends Strategy[T] {

  var _name: String = _
  var _ref: util.List[Strategy[T]] = _
  var _compositor: util.List[Compositor[T]] = _
  var _processor: util.List[Processor[T]] = _
  var _configParams: util.Map[Any, Any] = _

  val logger = Loggers.getLogger(classOf[LinearStrategy[T]])

  def processor: util.List[Processor[T]] = _processor

  def ref: util.List[Strategy[T]] = _ref

  def compositor: util.List[Compositor[T]] = _compositor

  def name: String = _name


  def initialize(name: String, alg: util.List[Processor[T]], ref: util.List[Strategy[T]], com: util.List[Compositor[T]], params: util.Map[Any, Any]): Unit = {
    this._name = name
    this._ref = ref
    this._compositor = com
    this._processor = alg
    this._configParams = params
  }

  def result(params: util.Map[Any, Any]): util.List[T] = {

    if (compositor != null && compositor.size() > 0) {

      var middleR = compositor.get(0).result(processor, ref, null, params)


      for (i <- 1 until compositor.size()) {
        middleR = compositor.get(i).result(processor, ref, middleR, params)
      }

      return middleR
    } else {
      return processor.get(0).result(params)
    }
  }


  def configParams: util.Map[Any, Any] = _configParams
}
