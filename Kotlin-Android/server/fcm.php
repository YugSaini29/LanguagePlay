<?php
/** FCM token registration — Android app se aata hai
 *  Ise /var/www/html/languageplay/api/fcm.php pe rakho
 */
header('Content-Type: application/json; charset=utf-8');
if ($_SERVER['REQUEST_METHOD'] !== 'POST') { http_response_code(405); echo '{"ok":false}'; exit; }
require __DIR__ . '/dbconfig.php';

/* table auto-create */
$mysqli->query("CREATE TABLE IF NOT EXISTS lp_devices (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT NOT NULL,
  token VARCHAR(255) NOT NULL,
  platform VARCHAR(20) DEFAULT 'android',
  updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY uq_token (token),
  KEY idx_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");

$b = json_decode(file_get_contents('php://input'), true) ?: [];
$user_key = preg_replace('/[^a-f0-9]/', '', (string)($b['user_key'] ?? ''));
$token = trim((string)($b['token'] ?? ''));
$platform = preg_replace('/[^a-z]/', '', (string)($b['platform'] ?? 'android'));

if (strlen($user_key) !== 32 || $token === '' || strlen($token) > 255) {
    echo json_encode(['ok' => false, 'error' => 'bad request']);
    exit;
}

$st = $mysqli->prepare("SELECT id FROM lp_users WHERE user_key=?");
$st->bind_param('s', $user_key);
$st->execute();
$u = $st->get_result()->fetch_assoc();
if (!$u) { echo json_encode(['ok' => false, 'error' => 'unknown user']); exit; }

$uid = (int)$u['id'];
$st = $mysqli->prepare(
    "INSERT INTO lp_devices (user_id, token, platform) VALUES (?,?,?)
     ON DUPLICATE KEY UPDATE user_id=VALUES(user_id), platform=VALUES(platform)");
$st->bind_param('iss', $uid, $token, $platform);
$st->execute();

echo json_encode(['ok' => true]);
