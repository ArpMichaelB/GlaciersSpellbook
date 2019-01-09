# Glacier's Spellbook
An application that is designed to keep track of Dungeons and Dragons caster.

Thanks to a plugin called gluon, this application is no longer confined to just a desktop, but is also an android application. 
I'm currently in the process of publishing this application on the google play store, but for now, just check out the releases page for an apk.

## However, lets spell out just what this application can do, view by view.

1. Pick Character
	  * Allows you to keep track of separate lists of spells, by character. 
	  * As an example, I have two characters that I run, Kyr'Nes and Dal'Rex. I can have one list of spells for Kyr'Nes, and one list of abilities for Dal'Rex, (who is not a caster, but can still get some utility out of this application).
2. Spell List
	* The premier feature of this application. Keeps track of all entered spells for the chosen character. Additionally, saves the damage dice necessary to roll for said spell. 
	* As an example, Kyr'Nes has a spell called Ice Knife, that has the potential to do 1d10 piercing damage, and 2d6 cold damage. There are some _conditions_ but they're unimportant for the example. Upon pressing the corresponding roll dice button, the application switches to the Roll Dice view, and has rolled one (1) ten (10) sided die, and two (2) six (6) sided dice.

3. Spell Search
	* Allows the user to search the SRD of Dungeons and Dragons. That is to say, any spell open sourced by Wizards of the Coast. 
		* This is courtesy of the dnd5e api, found [right here](https://github.com/adrpadua/5e-database).
    *  Of course, that's not every spell available to the game, which brings us to the next feature.
4. Custom Spell Input
	* This is what makes the application _endlessly_ useful, even to non-spellcasters.
	* This allows the user to input the spell's name and details. The spell used as an example earlier, Ice Knife, is not available in the search, so I had to input it manually. 
	* Speaking of examples, let's return to Dal'Rex, to specify how this is useful outside spells. Dal'Rex is a Fighter/Monk multiclass, and so has a monk ability called Radiant Sunbolt, which does 1d4 radiant damage. So, we can input the "spell" radiant sunbolt, and can now roll, rather effortlessly, the damage for said attack. This also works for a fighter ability such as second wind, which heals for 1d10+3, and therefore would have to be entered as 1d10+3 healing damage. 
	* It's a bit clunky to enter things this way, but the dice rolling from custom spells requires the string to contain a damage type and the word damage. 
5.  Rolling Dice
	* Finally, this application can roll dice for you. Whether this comes from the spell list or you're entering them manually, it'll keep track of your rolls for you. Provided you've typed a damage type, it'll keep track of that too. 
## Potential Trouble
No application is perfect, after all. I haven't tested the desktop release on anything but a windows machine, and the android application only works on phones that use internal memory over an SD card.
However, the only remaining error that I know of is related to writing/reading the files for each character, and as such, even on untested platforms, this application should still be able to use the dice rolling feature.
If there are any other errors I haven't caught, send me a description of just what caused the problem at glaciernester@gmail.com, and I'll do my best to reproduce and remove the bug.
## Releases
There should be a jar file for the desktop version, and an APK for the android version. I'll update the readme with a listing to the google play store once that's live.
## What in the WORLD is that icon?
It's a liquid scaled d20, because liquid scaling things makes me laugh unreasonably hard. Do enjoy the app, everyone!
