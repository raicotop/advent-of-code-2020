(ns aoc1.core-test
  (:require [clojure.test :refer :all]
            [aoc1.core :refer :all]))

(def task1 [1721
            979
            366
            299
            675
            1456])

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 514579 (sol1 task1)))
    (is (= 241861950 (sol2 task1)))))
