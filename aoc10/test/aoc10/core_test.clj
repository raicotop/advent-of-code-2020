(ns aoc10.core-test
  (:require [clojure.test :refer :all]
            [aoc10.core :refer :all]))

(def short-task
  "16
10
15
5
1
11
7
19
6
12
4")

(def long-task
  "28
33
18
42
31
14
46
20
48
47
24
23
49
45
19
38
39
11
1
32
25
35
8
17
7
9
4
2
34
10
3")

(deftest a-test
  (testing "first part"
    (is (= 35 (sol1 short-task)))
    (is (= 220 (sol1 long-task))))
  (testing "second task"
    (is (= 8 (sol2 short-task)))
    (is (= 19208 (sol2 long-task)))))