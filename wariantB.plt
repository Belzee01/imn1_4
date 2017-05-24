set terminal png size 800,600
set o 'Potential1.png'
set xlabel "x"
set ylabel "y"
set size ratio -1
set contour
set cntrparam levels 100
set view map
unset surface
unset key
splot 'warB_pot1.dat' u 1:2:3 w l title "Zad 1"

set terminal png size 800,600
set o 'VelocityX2.png'
set xl 'os X'
set yl 'os Y'
plot 'warB_vel_x_2.dat' u 1:2 w lines t 'Predkosc numerycznie'

set terminal png size 800,600
set o 'PotentialX0.png'
set xl 'os X'
set yl 'os Y'
plot 'warB_potnX0.dat' u 1:3 w lines t 'Potencjal w x = 0'

set terminal png size 800,600
set o 'PotentialX2.png'
set xl 'os X'
set yl 'os Y'
plot 'warB_potnX2.dat' u 1:3 w lines t 'Potencjal w x = 0'

set terminal png size 800,600
set o 'WirX0.png'
set xl 'os X'
set yl 'os Y'
plot 'warB_wirX0.dat' u 1:3 w lines t 'Wir w x = 0'

set terminal png size 800,600
set o 'WirX2.png'
set xl 'os X'
set yl 'os Y'
plot 'warB_wirX2.dat' u 1:3 w lines t 'Wir w x = 0'


##Zadanie 2
set terminal png size 800,600
set o 'PotentialQ10.png'
set xlabel "x"
set ylabel "y"
set size ratio -1
set contour
set cntrparam levels 100
set view map
unset surface
unset key
splot 'warB_potQ10.dat' u 1:2:3 w l title "Q = -10"

set terminal png size 800,600
set o 'PotentialQ100.png'
set xlabel "x"
set ylabel "y"
set size ratio -1
set contour
set cntrparam levels 100
set view map
unset surface
unset key
splot 'warB_potQ100.dat' u 1:2:3 w l title "Q = -100"

set terminal png size 800,600
set o 'PotentialQ500.png'
set xlabel "x"
set ylabel "y"
set size ratio -1
set contour
set cntrparam levels 100
set view map
unset surface
unset key
splot 'warB_potQ500.dat' u 1:2:3 w l title "Q = -500"


set terminal png size 800,600
set o 'PotentialQ1000.png'
set xlabel "x"
set ylabel "y"
set size ratio -1
set contour
set cntrparam levels 100
set view map
unset surface
unset key
splot 'warB_potQ1000.dat' u 1:2:3 w l title "Q = -1000"


set terminal png size 800,600
set o 'WirowoscQ1000.png'
set xlabel "x"
set ylabel "y"
set size ratio -1
set contour
set cntrparam levels 100
set view map
unset surface
unset key
splot 'warB_wirQ1000.dat' u 1:2:3 w l title "Wir Q = -1000"