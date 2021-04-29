package com.nktfh100.BouncyArrows.events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.util.Vector;

import com.nktfh100.BouncyArrows.main.BouncyArrows;

public class ProjectileHit implements Listener {

	private BouncyArrows plugin;

	public ProjectileHit(BouncyArrows instance) {
		plugin = instance;
	}

	@EventHandler
	public void onHit(ProjectileHitEvent ev) {
		if (ev.getEntity().getType() == EntityType.ARROW) {
			Entity shooter = (Entity) ev.getEntity().getShooter();
			if (shooter == null || shooter.getType() != EntityType.PLAYER || ev.getHitBlockFace() == null) {
				return;
			}

			Arrow oldArrow = (Arrow) ev.getEntity();
			if (oldArrow.getVelocity().length() < plugin.getConfigManager().getArrowMinSpeed()) {
				return;
			}
			int oldArrowBounces = 0;
			if (oldArrow.hasMetadata("bounces")) {
				List<MetadataValue> bounces_ = oldArrow.getMetadata("bounces");
				oldArrowBounces = bounces_.get(0).asInt();
				if (oldArrowBounces >= plugin.getConfigManager().getMaxBounces()) {
					return;
				}
			}

			Vector blockFaceDir = ev.getHitBlockFace().getDirection();

			Vector reflectionVector = oldArrow.getVelocity().clone().subtract(blockFaceDir.multiply(2.0D * oldArrow.getVelocity().dot(blockFaceDir)))
					.multiply(plugin.getConfigManager().getBounciness());

			Arrow newArrow = (Arrow) oldArrow.getWorld().spawn(oldArrow.getLocation(), Arrow.class);
			newArrow.setShooter(oldArrow.getShooter());
			oldArrow.remove();

			newArrow.setVelocity(reflectionVector);
			newArrow.setMetadata("bounces", new FixedMetadataValue(this.plugin, oldArrowBounces + 1));

			Bukkit.getServer().getPluginManager().callEvent(new ProjectileLaunchEvent(newArrow));
		}
	}
}
