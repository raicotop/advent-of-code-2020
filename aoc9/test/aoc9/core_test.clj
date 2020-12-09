(ns aoc9.core-test
  (:require [clojure.test :refer :all]
            [aoc9.core :refer :all]))

(def task
  "35
20
15
25
47
40
62
55
65
95
102
117
150
182
127
219
299
277
309
576")

(deftest a-test
  (testing "both parts"
    (is (= 127 (sol1 task 5)))
    (is (= 62 (sol2 task 5)))))