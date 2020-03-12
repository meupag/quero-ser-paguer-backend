window.onload = function () {

	console.log('client side js running ...');

	let form = document.querySelector('form');
	let addBtn = document.createElement('button');
	addBtn.type = 'button';
	addBtn.classList.add('btn', 'btn-primary');
	addBtn.style.marginRight = '150px';
	addBtn.textContent = '+';

	form.insertBefore(addBtn, form.lastChild);

	addBtn.addEventListener('click', addItem);

	checkItems();

	let counter = 1;
	let source = document.querySelector('.item');

	function addItem() {
		counter ++;

		let newItem = source.cloneNode(true);
		newItem.classList = '';
		newItem.classList.add('row', 'item' + counter);

		let remBtn = document.createElement('button');
		remBtn.type = 'button';
		remBtn.classList.add('btn', 'btn-primary');
		remBtn.style.marginLeft = '20px';
		remBtn.style.marginTop = '70px';
		remBtn.textContent = '-';

		let itemsgroup = document.querySelector('.itemsgroup');
		itemsgroup.appendChild(newItem);

		let column = document.querySelector('.item' + counter + ' .rem-btn');
		column.appendChild(remBtn);

		remBtn.addEventListener('click', remItem);

		let inputs = document.querySelectorAll('.item' + counter + ' input');
		for (let i = 0; i < inputs.length; i++) {
			inputs[i].value = '';
		};
	};

	function remItem(e) {
		let parent = e.target.parentElement.parentElement.parentElement;
		let child = e.target.parentElement.parentElement;

		parent.removeChild(child);
	};

	function checkItems() {
		let itemsgroup = document.querySelector('.itemsgroup');
		let columns = itemsgroup.querySelectorAll('.rem-btn');

		if (columns.length > 1) {
			for (let i = 1; i < columns.length; i++) {
				let remBtn = document.createElement('button');
				remBtn.type = 'button';
				remBtn.classList.add('btn', 'btn-primary');
				remBtn.style.marginLeft = '20px';
				remBtn.style.marginTop = '70px';
				remBtn.textContent = '-';

				remBtn.addEventListener('click', remItem);

				columns[i].appendChild(remBtn);
			};
		};
	};
};