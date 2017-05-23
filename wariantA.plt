set terminal png size 800,600
set o 'Potential1.png'
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
splot 'warA_pot.dat' u 1:2:3 w pm3d lt -1 t ''

set terminal png size 800,600
set o 'Wirowosc1.png'
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
splot 'warA_wir.dat' u 1:2:3 w pm3d lt -1 t ''

set terminal png size 800,600
set o 'VelocityI50.png'
set xl 'os X'
set yl 'os Y'
set title 'Wartosc predkosci w i =50'
plot 'warA_vel.dat' u 1:2 w lines t 'Predkosc numerycznie', \
'warA_vel.dat' u 1:3 w lines t 'Predkosc analiztycznie'

set terminal png size 800,600
set o 'StrumienI50.png'
set xl 'os X'
set yl 'os Y'
set title 'Funkcja strumienia w i = 50'
plot 'warA_pot_i_50.dat' u 1:2 w lines t 'Strumien numerycznie', \
'warA_pot_i_50.dat' u 1:3 w lines t 'Strumien analitycznie'

set terminal png size 800,600
set o 'WirowoscI50.png'
set xl 'os X'
set yl 'os Y'
set title 'Funckaj wiru w i =50'
plot 'warA_pot_i_50.dat' u 1:4 w lines t 'Wir numerycznie', \
'warA_pot_i_50.dat' u 1:5 w lines t 'Wir analitycznie'

set terminal png size 800,600
set o 'StrumienI250.png'
set xl 'os X'
set yl 'os Y'
set title 'Funkcja strumienia w i = 250'
plot 'warA_pot_i_250.dat' u 1:2 w lines t 'Strumien numerycznie', \
'warA_pot_i_250.dat' u 1:3 w lines t 'Strumien analitycznie'

set terminal png size 800,600
set o 'WirowoscI250.png'
set xl 'os X'
set yl 'os Y'
set title 'Funckaj wiru w i =250'
plot 'warA_pot_i_250.dat' u 1:4 w lines t 'Wir numerycznie', \
'warA_pot_i_250.dat' u 1:5 w lines t 'Wir analitycznie'


########
