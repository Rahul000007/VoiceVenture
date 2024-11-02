
---

# 🎧 Real-Time Matchmaking & Audio Call App

![Java](https://img.shields.io/badge/Java-17-orange)
![Vue.js](https://img.shields.io/badge/Vue.js-3.0-green)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.3-brightgreen)
![WebRTC](https://img.shields.io/badge/WebRTC-Audio%20Call-blue)
![WebSockets](https://img.shields.io/badge/WebSockets-Real--Time-ff69b4)

> A real-time matchmaking app that connects users and initiates a **WebRTC-based audio call** once both users accept the match! This app seamlessly integrates matchmaking, audio calling, security, and timeout handling for an immersive experience.


## 🌟 Features
- **Real-Time Matchmaking**: Connects users in real time based on availability.
- **Mutual Acceptance Matching**: Starts an audio call only if both users accept.
- **WebRTC Audio Calling**: Facilitates smooth, peer-to-peer audio communication.
- **Polling Mechanism for Matching**: Matches users without relying on simultaneous clicks.
- **Timeout Handling**: Automatically re-queues users if no response within 15 seconds.
- **Secure JWT Authentication**: Ensures secure communication and user data protection.

---

## ⚙️ Tech Stack

### Frontend
- **Vue.js 3**: Fast, reactive, and component-based frontend framework.
- **Vuex**: Manages state for matchmaking, user actions, and call status.
- **WebRTC**: Enables peer-to-peer audio streaming.
- **Polling**: Periodic server checks to update user match status.

### Backend
- **Java 17** & **Spring Boot**: RESTful backend with WebSocket support for real-time updates.
- **Spring WebSockets**: Handles real-time messaging between users.
- **Spring Scheduler**: Manages periodic checks for matching users.
- **JWT Authentication**: Manages user authentication securely, allowing token-based user verification for WebSocket and API endpoints.

[//]: # (- **Redis &#40;optional&#41;**: Used for caching active users in the matchmaking queue.)

## 🔒 Security
- **JWT Authentication**: Each user is authenticated via JSON Web Tokens (JWT) for secure, stateless sessions.
- **Token Validation:**: Verifies JWTs on each request, ensuring only authenticated users access matchmaking, call setup, and other private endpoints.
- **WebSocket Authentication**: JWT-based validation for WebSocket communication, ensuring that only authenticated users can receive match or call notifications.
- **WebSocket Authentication**: Ensures specific resources are accessible only to authorized user roles.

---

## 📂 Project Structure

### Frontend: Vue.js & WebRTC Integration

```plaintext
/src
├── components
│   ├── Matching.vue          # UI for matching and acceptance
│   └── AudioCall.vue         # WebRTC-based audio call UI
├── store
│   └── matchingStore.js      # Vuex store for match and call state
└── services
│    └── webSocketService.js   # WebSocket setup for real-time messaging
└── .......
    
```

### Backend: Spring Boot & Real-Time Matchmaking

```plaintext
/src/main/java/com/example/matchmaking
├── controller
│   └── MatchingController.java       # REST API for start/stop matching
├── model
│   └── MatchAcceptanceEvent.java     # Defines match events & acceptance
├── service
│   └── MatchingService.java          # Core logic for matchmaking
└── config
│   └── WebSocketConfig.java          # WebSocket config for real-time messaging  
└── .......
```

---

## 🚀 Getting Started

### Prerequisites
- **Node.js** (Frontend)
- **Java 17+** (Backend)

[//]: # (- **Redis** &#40;optional, for enhanced performance&#41;)

### Frontend Setup

1. **Navigate** to the frontend directory:
   ```bash
   cd frontend
   ```
2. **Install Dependencies**:
   ```bash
   npm install
   ```
3. **Run the App**:
   ```bash
   npm run serve
   ```

### Backend Setup

1. **Navigate** to the backend directory:
   ```bash
   cd backend
   ```
2. **Build the Project**:
   ```bash
   ./mvnw clean install
   ```
3. **Run the Spring Boot Application**:
   ```bash
   ./mvnw spring-boot:run
   ```

[//]: # (### Running Redis &#40;Optional&#41;)

[//]: # (   ```bash)

[//]: # (   redis-server)

[//]: # (   ```)

---

## 🛠️ Usage

1. **Start Matching**: Click **Start Matching** to enter the queue.
2. **Accept/Decline**: If a match is found, select to **Accept** or **Decline**.
3. **Audio Call**: Once both users accept, the WebRTC audio call starts!

---

## 🧩 How It Works

### Matching Flow
- **Match Search**: Backend searches for other active users through polling.
- **Real-Time Updates**: WebSocket notifies users of match status and acceptance.
- **Audio Call Trigger**: Both users’ acceptance triggers WebRTC audio call setup.

### Handling Timeout
- Users have **15 seconds** to accept/decline a match, after which the backend initiates a re-match.

---

### 🤝 Contributions

Feel free to fork, submit issues, or make pull requests! Every contribution is appreciated.

---