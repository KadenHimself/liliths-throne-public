package com.lilithsthrone.game.inventory;

import com.lilithsthrone.controller.xmlParsing.Element;
import com.lilithsthrone.controller.xmlParsing.XMLMissingTagException;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @since 0.1.0
 * @version 0.3.7.7
 * @author Innoxia
 */
public abstract class AbstractCoreType {
	
	@Override
	public boolean equals(Object o) {
		return true;
	}
	
	@Override
	public int hashCode() {
		return 1;
	}

	public Rarity getRarity() {
		return Rarity.COMMON;
	}

	/**
	 * @return A List of other AbstractItemTypes which should be added to the player's Encyclopedia when this one is discovered. Used for unique items in situations where acquiring one would make it impossible to ever see other ones.
	 */
	public List<AbstractCoreType> getAdditionalDiscoveryTypes() {
		return new ArrayList<>();
	}

	/**
	 * A Set of String tags that can be associated with an item type without modifying the ItemTags type.
	 */
	private final Set<String> modTags = new HashSet<>();

	protected Set<ItemTag> itemTags = new HashSet<>();

	public Set<ItemTag> getItemTags() {
		return itemTags;
	}

	/**
	 * Returns true if the item has any of the passed in tags.
	 *
	 * @param modTags
	 * @param itemTags
	 *
	 * @return
	 */
	public boolean hasAnyTags(final Set<String> modTags, final Set<ItemTag> itemTags) {
		return !Collections.disjoint(this.getItemTags(), itemTags) || !Collections.disjoint(this.getModTags(), modTags);
	}

	/**
	 * Returns all of the Mod Tags associated with this type.
	 *
	 * @return Set
	 */
	public Set<String> getModTags() {
		return modTags;
	}
	
	protected void loadModTags(final Element coreAttributes) {
		// Make sure we have a coreAttributes object.
		if (coreAttributes == null) {
			return;
		}

		// Load all string tags and add them.
		// TODO: Add a list of tags somewhere to allow for validation.
		coreAttributes.getOptionalFirstOf("modTags").ifPresent(new Consumer<Element>() {
			@Override
			public void accept(final Element modTagElement) {
				modTags.addAll(modTagElement.getAllOf("tag").stream()
						.map((tag) -> tag.getTextContent().trim())
						.collect(Collectors.toSet()));
			}
		});
	}

}
