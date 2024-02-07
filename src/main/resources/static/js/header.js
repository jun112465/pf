// category 추가 event listener 등록
let categoryAddBtn = document.getElementById("categoryAddBtn");
categoryAddBtn.addEventListener('click', ()=>{
    let categoryName = prompt("카테고리명을 정해주세요");

    if(categoryName.length == 0){
        console.log("nothing");
    }else{
        fetch("/category/add", {
            method: "POST",
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({
                'id' : null,
                'name' : categoryName,
                'userId' : null
            })
        })
            .then(json => json.json())
            .then(data => {
                if(data) location.reload();
                else alert("이미 있는 카테고리 이름입니다.")
                console.log(data)
            })
    }

})