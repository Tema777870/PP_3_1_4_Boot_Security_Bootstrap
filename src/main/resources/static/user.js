$(async function () {
    await loadUser();
});

async function loadUser() {
    fetch("http://localhost:8080/api/user")
        .then(r => r.json())
        .then(data => {
            $('#usernameUser').append(data.username);
            let roles = data.roles.map(role => " " + role.name.substring(5));
            $('#roleUser').append(roles);
            let user = `
            <tr>
                <td>${data.id}</td>
                <td>${data.name}</td>
                <td>${data.lastName}</td>
                <td>${data.age}</td>
                <td>${data.username}</td>
                <td>${data.password}</td>
                <td>${roles}</td>
            </tr>`;
            $('#oneUser').append(user);
        })
        .catch((error) => {
            alert(error);
        });
}











