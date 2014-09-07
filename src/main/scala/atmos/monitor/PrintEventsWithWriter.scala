/* PrintEventsWithWriter.scala
 * 
 * Copyright (c) 2013-2014 bizo.com
 * Copyright (c) 2013-2014 zman.io
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package atmos.monitor

import java.io.PrintWriter

/**
 * An event monitor that prints information about retry events to a writer.
 *
 * @param writer The writer that this event monitor prints to.
 * @param retryingAction The action that is performed when a retrying event is received.
 * @param interruptedAction The action that is performed when an interrupted event is received.
 * @param abortedAction The action that is performed when an aborted event is received.
 */
case class PrintEventsWithWriter(
  writer: PrintWriter,
  retryingAction: PrintAction = PrintEventsWithWriter.defaultRetryingAction,
  interruptedAction: PrintAction = PrintEventsWithWriter.defaultInterruptedAction,
  abortedAction: PrintAction = PrintEventsWithWriter.defaultAbortedAction)
  extends PrintEvents {

  /** @inheritdoc */
  def printMessage(message: String) = writer.println(message)

  /** @inheritdoc */
  def printMessageAndStackTrace(message: String, thrown: Throwable) = writer synchronized {
    writer.println(message)
    thrown.printStackTrace(writer)
  }

}

/**
 * Definitions associated with event monitors that print information about retry events as text.
 */
object PrintEventsWithWriter {

  import PrintAction._

  /** The action that is performed by default when a retrying event is received. */
  val defaultRetryingAction: PrintAction = PrintMessage

  /** The action that is performed by default when an interrupted event is received. */
  val defaultInterruptedAction: PrintAction = PrintMessageAndStackTrace

  /** The action that is performed by default when an aborted event is received. */
  val defaultAbortedAction: PrintAction = PrintMessageAndStackTrace

}