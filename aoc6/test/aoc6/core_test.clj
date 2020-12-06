(ns aoc6.core-test
  (:require [clojure.test :refer :all]
            [aoc6.core :refer :all]))


(def task
  "abc

a
b
c

ab
ac

a
a
a
a

b")

(deftest test
  (testing "first part"
    (is (= 11 (sol1 task)))
    (is (= 6587 (sol1 (slurp "resources/input.txt")))))
  (testing "second part"
    (is (= 6 (sol2 task)))
    (is (= 3235 (sol2 (slurp "resources/input.txt"))))))
