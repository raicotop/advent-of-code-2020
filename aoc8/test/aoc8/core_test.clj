(ns aoc8.core-test
  (:require [clojure.test :refer :all]
            [aoc8.core :refer :all]))

(def task
  "nop +0
acc +1
jmp +4
acc +3
jmp -3
acc -99
acc +1
jmp -4
acc +6")

(deftest a-test
  (testing "first task"
    (is (= 5 (sol1 task))))
  (testing "second task"
    (is (= 8 (sol2 task)))))


