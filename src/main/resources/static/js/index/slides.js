// javascript for gallery slide
// 즉시실행함수 IIFE(Immediately Invoked Function Expression)
(()=>{
    let slideIndex = 1;
    showSlides(slideIndex);

    // Next/previous controls
    function plusSlides(n) {
        showSlides(slideIndex += n);
    }

    // Thumbnail image controls
    function currentSlide(n) {
        showSlides(slideIndex = n);
    }

    function showSlides(n) {
        let i;
        let slides = document.getElementsByClassName("slide");
        // let dots = document.getElementsByClassName("demo");
        // let captionText = document.getElementById("caption");

        if (n > slides.length) {slideIndex = 1}
        if (n < 1) {slideIndex = slides.length}
        for (i = 0; i < slides.length; i++) {
            slides[i].style.display = "none";
        }
        // for (i = 0; i < dots.length; i++) {
        //     dots[i].className = dots[i].className.replace(" active", "");
        // }

        // if(slides[slideIndex-1].style.display)

        // if slide is login -> display : flex
        // else -> display : grid
        slides[slideIndex-1].style.display = "flex"

        // dots[slideIndex-1].className += " active";
        // captionText.innerHTML = dots[slideIndex-1].alt;
    }


    // add listener to buttons
    let prevBtn = document.getElementsByClassName("prev")[0]
    prevBtn.addEventListener('click', ()=>{
        plusSlides(-1)
    })

    let nextBtn = document.getElementsByClassName("next")[0]
    nextBtn.addEventListener('click', ()=>{
        plusSlides(1)
    })

})()
