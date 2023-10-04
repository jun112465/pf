(()=>{

    // convert htmlText to Html
    let contents = document.getElementsByClassName("content");
    for(let i=0; i<contents.length; i++) contents[i].innerHTML = contents[i].textContent;


})();