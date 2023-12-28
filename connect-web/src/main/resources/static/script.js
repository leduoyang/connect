const apiBaseUrl = '/api/connect/v1/user';
const userId = 'ROOT'; // 你的使用者ID
const profileContainer = document.getElementById('profile-container');

// 使用 fetch 透過 API 取得使用者資訊
fetch(`${apiBaseUrl}/${userId}`)
    .then(response => response.json())
    .then(data => {
        // 根據 API 回傳的資料渲染主頁面
        profileContainer.innerHTML = `
          <img src="${data.profileImage}" alt="Profile Image">
          <p>User ID: ${data.userId}</p>
          <p>Bio: ${data.description}</p>
          <p>Stats: Views - ${data.stats.views}, Followers - ${data.stats.followers}, Followings - ${data.stats.followings}</p>
          <p>Social Networks:</p>
          <ul>
            ${data.socialNetworks.map(network => `<li><a href="${network.url}">${network.name}</a></li>`).join('')}
          </ul>
          <p>Websites:</p>
          <ul>
            ${data.websites.map(website => `<li><a href="${website.url}">${website.name}</a></li>`).join('')}
          </ul>
        `;
    })
    .catch(error => console.error('Error fetching user data:', error));
