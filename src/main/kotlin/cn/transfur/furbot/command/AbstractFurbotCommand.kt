package cn.transfur.furbot.command

import cn.transfur.furbot.Config
import cn.transfur.furbot.KotlinPluginMain
import net.mamoe.mirai.console.command.CommandSenderOnMessage
import net.mamoe.mirai.console.command.FriendCommandSenderOnMessage
import net.mamoe.mirai.console.command.MemberCommandSenderOnMessage
import net.mamoe.mirai.console.command.SimpleCommand
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.contact.Contact

abstract class FurbotSimpleCommand(
    primaryName: String
) : SimpleCommand(KotlinPluginMain, primaryName), FurbotCommand {
    protected inline fun CommandSenderOnMessage<*>.differContact(action: (Contact) -> Unit) {
        when (this) {
            is MemberCommandSenderOnMessage -> {
                if (Config.furbot.respondGroups)
                    action(group)
            }
            is FriendCommandSenderOnMessage -> {
                if (Config.furbot.respondFriends)
                    action(user)
            }
        }
    }
}

abstract class FurbotCompositeCommand(
    primaryName: String
) : CompositeCommand(KotlinPluginMain, primaryName), FurbotCommand {
    protected inline fun CommandSenderOnMessage<*>.differContact(action: (Contact) -> Unit) {
        when (this) {
            is MemberCommandSenderOnMessage -> {
                if (Config.furbot.respondGroups)
                    action(group)
            }
            is FriendCommandSenderOnMessage -> {
                if (Config.furbot.respondFriends)
                    action(user)
            }
        }
    }
}