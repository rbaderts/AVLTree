

/**
 * Created by rbadertscher on 2/1/18.
 */


fun main(args: Array<String>) {

    var tree = buildTree(arrayOf(80, 20, 30, 40, -10, 31, 32))
    tree.print()

}

fun buildTree(values: Array<Int>) : AVLTree {
    var tree: AVLTree = AVLTree();
    for (v in values) {
        tree.addValue(v);
    }
    return tree;
}


class AVLTree {

    var root: Node? = null

    fun print(): String {
        var s = root!!.print(StringBuilder(), true, StringBuilder()).toString()
        println(s)
        return s
    }

    fun addValue(value: Int) {

        if (root != null) {
            var newChild = root!!.addValue(value)
            if (newChild != null) {
                root = newChild
            }
        } else {
            root = Node(value)
        }

    }


    class Node(var value: Int) {
        var left: Node? = null
        var right: Node? = null
        var depth: Int = 0
        var lean: Int = 0       // -1 left, 0 none, 1 right

        fun rotateR() :Node {
            var root: Node? = null

            // double rotation
            if (this.left != null && this.left!!.lean > 0) {
                this.left = this.left!!.rotateL()
            }

            root = this.left
            this.left = root!!.right
            root!!.right = this
            return root
        }

        fun rotateL() :Node {
            var root: Node? = null

            // double rotation
            if (this.right != null && this.right!!.lean < 0) {
                this.right = this.right!!.rotateR()
            }
            root = this.right
            this.right = root!!.left
            root!!.left = this
            return root
        }

        fun addValue(value: Int) :Node? {
            if (value < this.value) {
                if (left != null) {
                    var newChild = left!!.addValue(value)
                    if (newChild != null) {
                        left = newChild
                    } else {
                        if (left!!.depth >= depth) {
                            depth = left!!.depth + 1
                        }
                    }
                    if (depth >= 2) {
                       return rotateR();
                    }
                } else {
                    left = Node(value)
                    if (depth < 1) {
                        depth = 1
                    }
                    lean -= 1
                }
            } else {

                if (right != null) {
                    var newChild = right!!.addValue(value)
                    if (newChild != null) {
                        right = newChild

                    } else {
                        if (right!!.depth >= depth) {
                            depth += right!!.depth + 1
                        }
                    }

                    if (depth >= 2) {
                        return rotateL()
                    }
                } else {
                    right = Node(value)
                    if (depth < 1) {
                        depth = 1
                    }
                    lean += 1
                }

            }
            return null
        }


        fun print(prefix: StringBuilder, isTail: Boolean, sb: StringBuilder): StringBuilder {
            if (right != null) {
                right!!.print(StringBuilder().append(prefix).append(if (isTail) "│   " else "    "), false, sb)
            }
            sb.append(prefix).append(if (isTail) "└── " else "┌── ").
                    append(value.toString()).append(":").append(depth.toString()).append("\n")
            if (left != null) {
                left!!.print(StringBuilder().append(prefix).append(if (isTail) "    " else "│   "), true, sb)
            }
            return sb
        }
    }


}
