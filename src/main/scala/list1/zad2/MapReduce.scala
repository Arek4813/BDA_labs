import scala.collection.mutable.ListBuffer

object MapReduce {

  def main(args: Array[String]): Unit = {
    val nodes_and_edges = List[(Int, List[Int])](
//      (1, List(2, 3)),
//      (3, List(1, 5)),
//      (2, List(5)),
//      (5, List()),
      (1, List(2,3))
    )

    val mapper1 = new Mapper
//    val mapper2 = new Mapper
//    val mapper3 = new Mapper

    val reducer = new Reducer

    reducer.reduce(
      mapper1.map(nodes_and_edges.head)
    )

//    reducer.reduce(
//      mapper2.map(nodes_and_edges(1))
//    )
//
//    reducer.reduce(
//      mapper3.map(nodes_and_edges.slice(2,4))
//    )

    reducer.printer
  }
}
