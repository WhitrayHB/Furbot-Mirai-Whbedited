package cn.transfur.furbot.command.furbot

import cn.transfur.furbot.command.FriendAccessCommand
import cn.transfur.furbot.data.FurPic
import cn.transfur.furbot.util.sendMessage
import net.mamoe.mirai.console.command.CommandSenderOnMessage
import net.mamoe.mirai.contact.Contact

object GetFurRandCommand : GetFurCommand(
    apiPath = "api/v2/getFursuitRand",
    primaryName = "来只毛",
    description = "Get random fursuit from Tail API"
), FriendAccessCommand {
    suspend fun get(): FurPic { // Never 404
        return getFurPic()!!
    }

    @Handler
    suspend fun CommandSenderOnMessage<*>.run() = runBoth { target, _ -> respond(target) }

    private suspend fun respond(target: Contact) {
        val furPic = get()

        target.sendMessage {
            // Result text
            add("""
                --- 鸽子的吸毛 Bot ---
                今天也记得吸毛了呢~
                ID：${furPic.cid ?: furPic.fid}
                毛毛名字：${furPic.name}
            """.trimIndent())

            // Image
            addImage(target, furPic.url)

            // Tail
            addTail()
        }
    }
}