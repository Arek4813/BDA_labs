import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class Reducer {

  private val inv_graph = collection.mutable.Map[Int, ListBuffer[Int]]()

  def get_inv_graph: mutable.Map[Int, ListBuffer[Int]] = {
    inv_graph
  }

  def printer = {
    inv_graph.foreach(tuple => {
      var edges_str = ""
      tuple._2.foreach(edge => {
        edges_str = edges_str + edge + ", "
      })
      println(f"Node: ${tuple._1}, Edges: [${edges_str
        .reverse
        .dropWhile(_ == ' ')
        .dropWhile(_ == ',')
        .reverse}]")
    }
    )
  }

  def reduce(mapper_output: List[(Int, Int)]) = {
    mapper_output.foreach(tuple => {
      inv_graph.updateWith(tuple._1) {
        case Some(edges_list) => Some(edges_list.append(tuple._2))
        case None => Some(ListBuffer[Int](tuple._2))
      }
    })
  }
}
