set terminal png size 800,600
set o 'WarBRelaxationZad1.png'
set xl 'os X'
set yl 'os Y'
set view map
set size ratio -1
set title 'Zad2. Przeplyw potencjalny'
set contours
set contour base
set cntrparam levels 10
unset clabel
set pm3d
splot 'warB_pot.dat' u 1:2:3 w pm3d lt -1 t ''

set terminal png size 800,600
set o 'WarBIntegralZad1.png'
set xl 'iteracja'
set yl 'wrtosc calki'
set size ratio -1
unset pm3d
set title 'Wartosc calki w kolejnych iteracjach'
set log x
set log y
plot 'warB_integral.dat' with line

set terminal png size 800,600
set o 'WarBRelaxationZad2.png'
unset log x
unset log y
set xl 'os X'
set yl 'os Y'
set view map
set size ratio -1
set title 'Zad2. Przeplyw potencjalny'
set contours
set contour base
set cntrparam levels 30
unset clabel
set pm3d
splot 'warB_pot2.dat' u 1:2:3 w pm3d lt -1 t ''

set terminal png size 800,600
set o 'WarBIntegralZad2.png'
set xl 'iteracja'
set yl 'wrtosc calki'
set size ratio -1
unset pm3d
set title 'Wartosc calki w kolejnych iteracjach'
set log x
set log y
plot 'warB_integral2.dat' with line





#### From A
set terminal png size 800,600
set o 'StrumienI50.png'
set xl 'os X'
set yl 'os Y'
set title 'Funkcja strumienia w i = 50'
plot 'warA_i50.dat' u 1:2 w lines t 'Strumien numerycznie', \
'warA_i50.dat' u 1:3 w lines t 'Strumien analitycznie'

set terminal png size 800,600
set o 'WirowoscI50.png'
set xl 'os X'
set yl 'os Y'
set title 'Funckaj wiru w i =50'
plot 'warA_i50.dat' u 1:4 w lines t 'Wir numerycznie', \
'warA_i50.dat' u 1:5 w lines t 'Wir analitycznie'

set terminal png size 800,600
set o 'StrumienI250.png'
set xl 'os X'
set yl 'os Y'
set title 'Funkcja strumienia w i = 250'
plot 'warA_i250.dat' u 1:2 w lines t 'Strumien numerycznie', \
'warA_i250.dat' u 1:3 w lines t 'Strumien analitycznie'

set terminal png size 800,600
set o 'WirowoscI250.png'
set xl 'os X'
set yl 'os Y'
set title 'Funckaj wiru w i =250'
plot 'warA_i250.dat' u 1:4 w lines t 'Wir numerycznie', \
'warA_i250.dat' u 1:5 w lines t 'Wir analitycznie'



set terminal png size 800,600
set o 'Potential2Q1.png'
set xl 'os X'
set yl 'os Y'
set view map
set size ratio -1
set title 'Zad2. Przeplyw potencjalny'
set contours
set contour base
set cntrparam levels 30
unset clabel
set pm3d
splot 'warA_pot2_Q1.dat' u 1:2:3 w pm3d lt -1 t ''


set terminal png size 800,600
set o 'Potential2Q2.png'
set xl 'os X'
set yl 'os Y'
set view map
set size ratio -1
set title 'Zad2. Przeplyw potencjalny'
set contours
set contour base
set cntrparam levels 30
unset clabel
set pm3d
splot 'warA_pot2_Q2.dat' u 1:2:3 w pm3d lt -1 t ''

set terminal png size 800,600
set o 'Potential2Q3.png'
set xl 'os X'
set yl 'os Y'
set view map
set size ratio -1
set title 'Zad2. Przeplyw potencjalny'
set contours
set contour base
set cntrparam levels 30
unset clabel
set pm3d
splot 'warA_pot2_Q3.dat' u 1:2:3 w pm3d lt -1 t ''