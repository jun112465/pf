(()=>{
    // 선택된 areaItem 에 밑줄이 생기도록 하는 기능
    let areaItems = document.getElementsByClassName("area-item")
    let allPosts = areaItems[0];
    let userPosts = areaItems[1];

    allPosts.addEventListener('click', ()=>{
        allPosts.id = 'selected';
        userPosts.id = 'unselected';
    })

    userPosts.addEventListener('click', ()=>{
        userPosts.id = 'selected';
        allPosts.id = 'unselected';
    })
})()