(ns aoc3.core-test
  (:require [clojure.test :refer :all]
            [aoc3.core :refer :all]))

(def test-task
  "..##.......
#...#...#..
.#....#..#.
..#.#...#.#
.#...##..#.
..#.##.....
.#.#.#....#
.#........#
#.##...#...
#...##....#
.#..#...#.#")

(deftest task
  (testing "first task"
    (is (= 7 (sol1 test-task)))
    (is (= 211 (sol1 (slurp "resources/input.txt")))))
  (testing "partial solution"
    (is (= 2 (sol-part test-task 1 1)))
    (is (= 7 (sol-part test-task 3 1)))
    (is (= 3 (sol-part test-task 5 1)))
    (is (= 4 (sol-part test-task 7 1)))
    (is (= 2 (sol-part test-task 1 2))))
  (testing "second task"
    (is (= 336 (sol2 test-task)))
    (is (= 3584591857 (sol2 (slurp "resources/input.txt"))))))