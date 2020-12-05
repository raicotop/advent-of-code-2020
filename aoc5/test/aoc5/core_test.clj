(ns aoc5.core-test
  (:require [clojure.test :refer :all]
            [aoc5.core :refer :all]))

(def task
  "BFFFBBFRRR
   FFFBBBFRRR
   BBFFBBFRLL")

(deftest solution
  (testing "first part"
    (is (= 820 (sol1 task)))
    (is (= 816 (sol1 (slurp "resources/input.txt")))))
  (testing "second part"
    (is (= 539 (sol2 (slurp "resources/input.txt"))))))
