let timerStartBtn = document.getElementById("timer-start")
let timerStopBtn = document.getElementById("timer-stop")
let timerResetBtn = document.getElementById("timer-reset")
let timer = document.getElementById("timer")


timerStartBtn.addEventListener('click', ()=>{

    let timerInterval = setInterval(()=>{
        let time = timer.innerHTML.split(":")
        let h = Number(time[0])
        let m = Number(time[1])
        let s = Number(time[2])

        time = h*3600 + m*60 + s + 1

        h = parseInt(time/3600)
        h = ('00'+h).slice(-2)
        time %= 3600

        m = parseInt(time/60)
        m = ('00'+m).slice(-2)
        time %= 60

        s = time
        s = ('00'+s).slice(-2)

        timer.innerHTML = h+ ":" + m + ":" + s
        console.log(h, m, s)
    }, 1000)
    timerStopBtn.type = "button"
    timerStartBtn.type = "hidden"

    timerStopBtn.addEventListener('click', ()=>{
        clearInterval(timerInterval)
        timerStopBtn.type = "hidden"
        timerStartBtn.type = "button"
    })
})

timerResetBtn.addEventListener('click', ()=>{
    timer.innerHTML="00:00:00"
})
