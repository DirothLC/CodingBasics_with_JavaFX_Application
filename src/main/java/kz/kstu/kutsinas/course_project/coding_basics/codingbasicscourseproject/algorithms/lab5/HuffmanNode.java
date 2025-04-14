package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.algorithms.lab5;

class HuffmanNode implements Comparable<HuffmanNode> {
    int value;
    int frequency;
    HuffmanNode left, right;

    HuffmanNode(int value, int frequency) {
        this.value = value;
        this.frequency = frequency;
    }

    HuffmanNode(HuffmanNode left, HuffmanNode right) {
        this.value = -1;
        this.frequency = left.frequency + right.frequency;
        this.left = left;
        this.right = right;
    }

    public boolean isLeaf() {
        return left == null && right == null;
    }

    public int compareTo(HuffmanNode o) {
        return Integer.compare(this.frequency, o.frequency);
    }
}

