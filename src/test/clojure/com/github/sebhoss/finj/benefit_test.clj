(ns com.github.sebhoss.finj.benefit-test
  (:require [com.github.sebhoss.finj.benefit :refer :all]
            [com.github.sebhoss.finj.predicate :refer :all]
            [clojure.test :refer :all]))

(deftest final-due-value-test
  (testing "with fuzzy results"
    (is (≈ 110 (final-due-value
                         :payment 100
                         :accumulation-factor 1.1
                         :period 1) 0.00001))
    (is (≈ 231 (final-due-value
                         :payment 100
                         :accumulation-factor 1.1
                         :period 2) 0.00001))))

(deftest final-immediate-value-test
  (testing "with exact results"
    (is (= 100.0 (final-immediate-value
                   :payment 100
                   :accumulation-factor 1.1
                   :period 1)))
    (is (= 210.0 (final-immediate-value
                   :payment 100
                   :accumulation-factor 1.1
                   :period 2)))))

(deftest present-due-value-test
  (testing "with positive values"
    (is (≈ 190 (present-due-value
                 :payment 100
                 :accumulation-factor 1.1
                 :period 2)))))

(deftest present-immediate-value-test
  (testing "with positive values"
    (is (≈ 173 (present-immediate-value
                 :payment 100
                 :accumulation-factor 1.1
                 :period 2)))))

(deftest perpetuity-due-value-test
  (testing "with positive values"
    (is (≈ 1100 (perpetuity-due-value
                  :payment 100
                  :accumulation-factor 1.1)))))

(deftest perpetuity-immediate-value-test
  (testing "with positive values"
    (is (≈ 1000 (perpetuity-immediate-value
                  :payment 100
                  :accumulation-factor 1.1)))))

(deftest period-test
  (testing "with positive values"
    (is (= 2.0 (period
                 :payment 100
                 :accumulation-factor 1.1
                 :final-immediate-value 210)))))