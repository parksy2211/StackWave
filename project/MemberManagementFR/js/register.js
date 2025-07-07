document.getElementById('memberForm').addEventListener('submit',e=>{
    e.preventDefault();
    const f=e.target;
    const member={
        name:f.name.value.trim(),
        age:parseInt(f.age.value),
        gender:f.gender.value,
        email:f.email.value.trim(),
        phone:f.phone.value.trim(),
        address:f.address.value.trim()
    };
    fetch("http://localhost:8080/api/members",{method:"POST",headers:{"Content-Type":"application/json"},body:JSON.stringify(member)})
        .then(r=>{if(!r.ok)return r.json().then(err=>Promise.reject(err));return r.json();})
        .then(()=>{
            alert("회원이 등록되었습니다.");
            location.href="index.html";
        })
        .catch(err=>{
            alert(Object.values(err).join("\n")||"등록 실패");
        });
});
