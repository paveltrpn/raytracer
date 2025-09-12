let SessionLoad = 1
let s:so_save = &g:so | let s:siso_save = &g:siso | setg so=0 siso=0 | setl so=-1 siso=-1
let v:this_session=expand("<sfile>:p")
silent only
silent tabonly
cd /mnt/main/code/raytracer
if expand('%') == '' && !&modified && line('$') <= 1 && getline(1) == ''
  let s:wipebuf = bufnr('%')
endif
let s:shortmess_save = &shortmess
if &shortmess =~ 'A'
  set shortmess=aoOA
else
  set shortmess=aoO
endif
badd +1 tire/js/main.js
badd +27 server/cmd/server.go
badd +293 tire/js/algebra/mtrx4.js
badd +2 health://
badd +66 /mnt/main/go_latest/src/net/http/status.go
badd +1 /mnt/main/code/tire/modules/render/gl/rendergl.cpp
badd +56 /mnt/main/code/tire/modules/render/render.cpp
badd +25 /mnt/main/code/tire/modules/config/config.cpp
badd +1 tire/js/render/context.js
badd +1 tire/js/render/geometry.js
badd +2 server/server.js
argglobal
%argdel
set stal=2
tabnew +setlocal\ bufhidden=wipe
tabrewind
edit server/server.js
let s:save_splitbelow = &splitbelow
let s:save_splitright = &splitright
set splitbelow splitright
wincmd _ | wincmd |
vsplit
wincmd _ | wincmd |
vsplit
2wincmd h
wincmd w
wincmd w
let &splitbelow = s:save_splitbelow
let &splitright = s:save_splitright
wincmd t
let s:save_winminheight = &winminheight
let s:save_winminwidth = &winminwidth
set winminheight=0
set winheight=1
set winminwidth=0
set winwidth=1
exe 'vert 1resize ' . ((&columns * 155 + 234) / 468)
exe 'vert 2resize ' . ((&columns * 156 + 234) / 468)
exe 'vert 3resize ' . ((&columns * 155 + 234) / 468)
argglobal
balt server/cmd/server.go
setlocal foldmethod=manual
setlocal foldexpr=0
setlocal foldmarker={{{,}}}
setlocal foldignore=#
setlocal foldlevel=0
setlocal foldminlines=1
setlocal foldnestmax=20
setlocal foldenable
silent! normal! zE
let &fdl = &fdl
let s:l = 2 - ((1 * winheight(0) + 51) / 103)
if s:l < 1 | let s:l = 1 | endif
keepjumps exe s:l
normal! zt
keepjumps 2
normal! 023|
wincmd w
argglobal
if bufexists(fnamemodify("tire/js/algebra/mtrx4.js", ":p")) | buffer tire/js/algebra/mtrx4.js | else | edit tire/js/algebra/mtrx4.js | endif
if &buftype ==# 'terminal'
  silent file tire/js/algebra/mtrx4.js
endif
balt tire/js/main.js
setlocal foldmethod=manual
setlocal foldexpr=0
setlocal foldmarker={{{,}}}
setlocal foldignore=#
setlocal foldlevel=0
setlocal foldminlines=1
setlocal foldnestmax=20
setlocal foldenable
silent! normal! zE
let &fdl = &fdl
let s:l = 306 - ((34 * winheight(0) + 51) / 103)
if s:l < 1 | let s:l = 1 | endif
keepjumps exe s:l
normal! zt
keepjumps 306
normal! 059|
wincmd w
argglobal
if bufexists(fnamemodify("tire/js/main.js", ":p")) | buffer tire/js/main.js | else | edit tire/js/main.js | endif
if &buftype ==# 'terminal'
  silent file tire/js/main.js
endif
balt server/cmd/server.go
setlocal foldmethod=manual
setlocal foldexpr=0
setlocal foldmarker={{{,}}}
setlocal foldignore=#
setlocal foldlevel=0
setlocal foldminlines=1
setlocal foldnestmax=20
setlocal foldenable
silent! normal! zE
let &fdl = &fdl
let s:l = 22 - ((19 * winheight(0) + 51) / 103)
if s:l < 1 | let s:l = 1 | endif
keepjumps exe s:l
normal! zt
keepjumps 22
normal! 0
wincmd w
2wincmd w
exe 'vert 1resize ' . ((&columns * 155 + 234) / 468)
exe 'vert 2resize ' . ((&columns * 156 + 234) / 468)
exe 'vert 3resize ' . ((&columns * 155 + 234) / 468)
tabnext
edit tire/js/render/context.js
let s:save_splitbelow = &splitbelow
let s:save_splitright = &splitright
set splitbelow splitright
wincmd _ | wincmd |
vsplit
wincmd _ | wincmd |
vsplit
2wincmd h
wincmd w
wincmd w
let &splitbelow = s:save_splitbelow
let &splitright = s:save_splitright
wincmd t
let s:save_winminheight = &winminheight
let s:save_winminwidth = &winminwidth
set winminheight=0
set winheight=1
set winminwidth=0
set winwidth=1
exe 'vert 1resize ' . ((&columns * 155 + 234) / 468)
exe 'vert 2resize ' . ((&columns * 156 + 234) / 468)
exe 'vert 3resize ' . ((&columns * 155 + 234) / 468)
argglobal
setlocal foldmethod=manual
setlocal foldexpr=0
setlocal foldmarker={{{,}}}
setlocal foldignore=#
setlocal foldlevel=0
setlocal foldminlines=1
setlocal foldnestmax=20
setlocal foldenable
silent! normal! zE
let &fdl = &fdl
let s:l = 1 - ((0 * winheight(0) + 51) / 103)
if s:l < 1 | let s:l = 1 | endif
keepjumps exe s:l
normal! zt
keepjumps 1
normal! 0
wincmd w
argglobal
if bufexists(fnamemodify("tire/js/render/geometry.js", ":p")) | buffer tire/js/render/geometry.js | else | edit tire/js/render/geometry.js | endif
if &buftype ==# 'terminal'
  silent file tire/js/render/geometry.js
endif
balt tire/js/render/context.js
setlocal foldmethod=manual
setlocal foldexpr=0
setlocal foldmarker={{{,}}}
setlocal foldignore=#
setlocal foldlevel=0
setlocal foldminlines=1
setlocal foldnestmax=20
setlocal foldenable
silent! normal! zE
let &fdl = &fdl
let s:l = 1 - ((0 * winheight(0) + 51) / 103)
if s:l < 1 | let s:l = 1 | endif
keepjumps exe s:l
normal! zt
keepjumps 1
normal! 0
wincmd w
argglobal
enew
balt server/cmd/server.go
setlocal foldmethod=manual
setlocal foldexpr=0
setlocal foldmarker={{{,}}}
setlocal foldignore=#
setlocal foldlevel=0
setlocal foldminlines=1
setlocal foldnestmax=20
setlocal foldenable
wincmd w
exe 'vert 1resize ' . ((&columns * 155 + 234) / 468)
exe 'vert 2resize ' . ((&columns * 156 + 234) / 468)
exe 'vert 3resize ' . ((&columns * 155 + 234) / 468)
tabnext 1
set stal=1
if exists('s:wipebuf') && len(win_findbuf(s:wipebuf)) == 0 && getbufvar(s:wipebuf, '&buftype') isnot# 'terminal'
  silent exe 'bwipe ' . s:wipebuf
endif
unlet! s:wipebuf
set winheight=1 winwidth=20
let &shortmess = s:shortmess_save
let &winminheight = s:save_winminheight
let &winminwidth = s:save_winminwidth
let s:sx = expand("<sfile>:p:r")."x.vim"
if filereadable(s:sx)
  exe "source " . fnameescape(s:sx)
endif
let &g:so = s:so_save | let &g:siso = s:siso_save
set hlsearch
nohlsearch
doautoall SessionLoadPost
unlet SessionLoad
" vim: set ft=vim :
