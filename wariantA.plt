set terminal png size 800,600
set o 'RelaxationZad1.png'
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
splot 'warA_pot1.dat' u 1:2:3 w pm3d lt -1 t ''

set terminal png size 800,600
set o 'RelaxationZad2.png'
set xl 'os X'
set yl 'os Y'
set view map
set size ratio -1
set title 'Zad2. Wir'
set contours
set contour base
set cntrparam levels 30
unset clabel
set pm3d
splot 'warA_wir1.dat' u 1:2:3 w pm3d lt -1 t ''