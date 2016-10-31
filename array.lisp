
(defparameter hello (make-array 10))
(setf *random-state* (make-random-state t)) ;seed for random

(setf (aref hello 0) 'Bdsfsob)
(setf (aref hello 1) 'Bob)
(setf (aref hello 2) 'Kill)
(setf (aref hello 3) 'Mega)
(setf (aref hello 4) 'Mesfga)
(setf (aref hello 5) 'Messga)
(setf (aref hello 6) 'Megdsfsda)
(setf (aref hello 7) 'Megdsfa)
(setf (aref hello 8) 'Megsdfa)
(setf (aref hello 9) 'Megadf)
(setf (aref hello 10) 'Bodfsdb)


(write (aref hello 0))
