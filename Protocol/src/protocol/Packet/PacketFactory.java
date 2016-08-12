package protocol.Packet;

import org.json.simple.JSONObject;

/**
 * Created by Lee on 2016-05-31.
 */
public interface PacketFactory {
    JSONObject join(String user_id, String user_pw, String user_name, int job_id);  // 회원 가입 패킷
    JSONObject login(String user_id, String user_pw);                         // 로그인 패킷 요청
    JSONObject character(long uuid, String name, int level, int job_id);    // 캐릭터 데이터 패킷
    JSONObject npc(long entityId, long npcId, int hp, int mp, int x, int y);    // NPC 데이터 패킷
    JSONObject move(long entity_id, long map_id, int dest_x, int dest_y);       // 이동 패킷
    JSONObject chat(long entity_id, String content);                              // 채팅 패킷
    JSONObject attack(long entityId);                                             // 공격했다는 패킷
    JSONObject damaging(long entityId, long mapId, long targetId, int damage);  // 데미지를 주었다는 패킷
    JSONObject levelUp(long entity_id, int new_level);                            // 레벨업 패킷
    JSONObject updateExp(int exp);                                                // 경험치 업데이트 패킷
    JSONObject notify(String content);
    JSONObject entityUpdate(long entityId, int entityHp);
}
