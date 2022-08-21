package cn.transfur.furbot.command.furbot

import cn.transfur.furbot.command.FriendAccessCommand
import cn.transfur.furbot.command.SessionCommand
import cn.transfur.furbot.data.FurPic
import cn.transfur.furbot.util.sendMessage
import net.mamoe.mirai.console.command.CommandSenderOnMessage
import net.mamoe.mirai.contact.Contact

object GetFurByIdCommand : GetFurCommand(
    apiPath = "api/v2/getFursuitByID",
    primaryName = "找毛图",
    description = "Get fursuit based on id from Tail API"
), SessionCommand, FriendAccessCommand {
    suspend fun get(id: String): FurPic? {
        return getFurPic("fid" to id)
    }

    @Handler
    suspend fun CommandSenderOnMessage<*>.run() = runBoth { target, sender ->
        val id = sender.ask("你想找的图片id是？") ?: return@runBoth
        respond(target, id)
    }

    @Handler
    suspend fun CommandSenderOnMessage<*>.run(id: String) = runBoth { target, _ -> respond(target, id) }

    private suspend fun respond(target: Contact, id: String) {
        val furPic = get(id)

        if (furPic == null) {
            target.sendMessage("这只毛毛还没有被收录，请联系绒狸API开发者添加")
        } else {
            target.sendMessage {
                // Result text
                add("""
                    --- 鸽子的吸毛 Bot ---
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
}