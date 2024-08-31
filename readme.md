
# Rentable Holograms
- Rentable Holograms is a plugin to allow players advertise or create cool messages using an in-game hologram they can buy using in-game currency. It features **impressive moderation tools** and **amazing Developer API** with tons of features and great support.

# Features
- Everything is being edited using GUI
- Custom items support 

## Commands - Permissions - Description

| Command                           | Permissions                                 | Descriptions                                           |
|-----------------------------------|---------------------------------------------|--------------------------------------------------------|
| /rentableholograms (/rh)          | rentableholograms.commands.rentableholograms | Provides a helpful page containing all commands|
| /rentableholograms create {price} | rentableholograms.commands.create| Creates a hologram on player's location with set price|
| /rentableholograms delete {id}    | rentableholograms.commands.delete| Deletes the hologram associated with the given id|
| /rentableholograms reload         | rentableholograms.commands.reload | Reloads all config files and hologram's info|
| /rentableholograms identify       | rentableholograms.commands.identify | Reveals above each hologram their associated id|


# Configuration
You can decide which material and name each function and feature will get, by easily editing **config.yml**.

<details>
<summary>Config.yml</summary>

## Example config.yml
```yml
# Rentable Holograms by ItzGuy
#
# Redistribution or reselling this plugin will result in your access to the plugin removed!

holograms-layouts:
  # default hologram variables:
  # %price% - price for a day of rental
  # %id% - hologram identifier from holograms.yml
  default:
    - "&a&nRent a hologram"
    - ""
    - "&7Right click this hologram to rent it!"
    - "&7Price for a day of rental is: &e%price%"
    - "&8Hologram ID: %id%"

  # rented hologram variables:
  # %lines% - the hologram lines the player has chosen
  # %player% - player who rented the hologram
  # %time% - the time remaining of the rental (returns a value formatted: 0D 2H 26M 15S)
  # %price% - price for a day of rental
  # %id% - hologram identifier from holograms.yml
  rented:
    - "&a&nRented hologram"
    - ""
    - "%lines%"
    - ""
    - "&7Hologram rented by: &e%player%"
    - "&7Rental ends in: &e%time%"
    - "&8Hologram ID: %id%"

settings:
  # the amount of lines players will be able to edit
  # ranges between 1-7 any higher or lower from these range will cause errors
  lines: 4

  # the maximum days players will be able to rent the hologram and won't allow any higher
  # if set to 0 it will count as infinite
  max-days: 3

  # the maximum amount of holograms players will be able to rent at a time
  max-holograms: 1

  # the starting amount of days players will recieve apon renting a hologram
  starting-days: 1

  # the method which the plugin takes input from players. (ANVIL (MAX 30 CHARACTERS), CHAT)
  user-input: CHAT

  # discord webhook to log edited lines, leave empty to disable
  webhook: ""
```
</details>

# To Do
- [x] Create a description
- [ ] Create a javadoc for the sourcecode

# Contact
If you encounter any issue or want to ask a question, Feel free to contact me using the following contacts:
### Discord
* itzguy.
### Gmail
* guy.asayag@gmail.com
### SpigotMC
* [ItzGuy](https://www.spigotmc.org/members/guytheking152.376028/)
