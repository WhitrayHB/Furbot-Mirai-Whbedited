package cn.transfur.furbot

import cn.transfur.furbot.command.CommandRunner
import cn.transfur.furbot.network.TailApiClient
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.events.FriendMessageEvent
import net.mamoe.mirai.event.events.GroupMessageEvent

object KotlinPluginMain : KotlinPlugin(
    JvmPluginDescription(
        id = "cn.transfur.furbot",
        version = "0.1.2",
        name = "Furbot"
    ) {
        author("Jmeow, Peanuuutz")
        info("furbot-mirai")
    }
) {
    override fun onEnable() {
        Config.reload()

        TailApiClient.open()

        subscribe()
    }

    private fun subscribe() {
        val channel = GlobalEventChannel.parentScope(this)
        channel.subscribeAlways<GroupMessageEvent> {
            if (!Config.furbot.respondGroups)
                return@subscribeAlways
            CommandRunner.run(this)
        }
        channel.subscribeAlways<FriendMessageEvent> {
            if (!Config.furbot.respondFriends)
                return@subscribeAlways
            CommandRunner.run(this)
        }
    }

    override fun onDisable() {
        TailApiClient.close()
    }
}
